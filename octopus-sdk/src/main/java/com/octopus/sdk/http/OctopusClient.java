/*
 * Copyright (c) Octopus Deploy and contributors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * these files except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.octopus.sdk.http;

import static java.util.Collections.singletonList;

import com.octopus.sdk.Constants;
import com.octopus.sdk.ServerInformation;
import com.octopus.sdk.exceptions.OctopusRequestException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.octopus.sdk.logging.Logger;
import com.octopus.sdk.resolver.Resolver;
import com.octopus.sdk.resolver.SpaceResolver;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;

public class OctopusClient {
  private static final String ROOT_PATH = "api";

  private final OkHttpClient httpClient;
  private final Gson gson;
  private final URL serverUrl;
  private final Map<String, List<String>> requiredHeaders = new HashMap<>();

  private final Logger logger;

  private final Resolver resolver;
  public final SpaceResolver spaceResolver;
  @Nullable
  private ServerInformation serverInformation;

  OctopusClient(final OkHttpClient httpClient, final URL serverUrl, final Logger logger) {
    this.httpClient = httpClient;
    this.serverUrl = serverUrl;
    requiredHeaders.put("Content-Type", singletonList("application/json"));
    requiredHeaders.put("Accept-encoding", singletonList("application/json"));

    this.gson = new GsonBuilder()
        .registerTypeAdapter(
            OffsetDateTime.class, new GsonTypeConverters.OffsetDateTimeDeserializer())
        .registerTypeAdapter(
            OffsetDateTime.class, new GsonTypeConverters.OffsetDateTimeSerializer())
        .disableHtmlEscaping()
        .create();

    this.logger = logger;
    this.resolver = new Resolver(serverUrl.toString());
    this.spaceResolver = new SpaceResolver();
  }

  OctopusClient(final OkHttpClient httpClient, final URL serverUrl, final String apiKey, final Logger logger) {
    this(httpClient, serverUrl, logger);
    requiredHeaders.put("X-Octopus-ApiKey", singletonList(apiKey));
  }

  public URL getServerUrl() {
    return serverUrl;
  }

  // TODO: Get Capabilities

  public <TResource> TResource get(final String path, @Nullable Map<String, Object> args, final Class<TResource> responseType) throws IOException {
    if (path == null) {
      throw new Error("path parameter was not set");
    }

    final String url = this.resolveUrl(path, args);
    final Call call = this.createCall(url, "GET", null);

    return executeCall(call, responseType);
  }

  public <TBody, TResource> TResource post(
      final String endpoint, final TBody bodyObject, final Class<TResource> responseType)
      throws IOException {
    final Call call = createCallWithJsonBody(endpoint, "POST", bodyObject);
    return executeCall(call, responseType);
  }


  public <TReturn> TReturn doCreate(String path, @Nullable Object command, @Nullable Map<String, Object> args, final Class<TReturn> responseType) throws IOException {
    return this.doCommand("POST", path, command, args, responseType);
  }

  public <TReturn> TReturn doUpdate(String path, @Nullable Object command, @Nullable Map<String, Object> args, final Class<TReturn> responseType) throws IOException {
    return this.doCommand("PUT", path, command, args, responseType);
  }

  private <TReturn> TReturn doCommand(String verb, String path, @Nullable Object command, @Nullable Map<String, Object> args, final Class<TReturn> responseType) throws IOException {
    Map<String, Object> req = this.convertObjToMap(command);
    if (req.containsKey("spaceName")) {
      final String spaceName = (String) req.get("spaceName");
      final String spaceId = spaceResolver.resolveSpaceId(this, spaceName);
      args.put("spaceId", spaceId);
      req.put("spaceId", spaceId);
    }

    if (args != null && args.containsValue("spaceName")) {
      final String spaceId = spaceResolver.resolveSpaceId(this, (String) args.get("spaceName"));
      args.put("spaceId", spaceId);
    }

    final String url = this.resolveUrl(path, args);
    final Call call = this.createCallWithJsonBody(url, verb, command);
    return executeCall(call, responseType);
  }

  public <TReturn> TReturn request(String path, @Nullable Object request, Class<TReturn> responseType) throws IOException {
    Map<String, Object> req = null;
    if (request != null) {
      req = this.convertObjToMap(request);
    }
    return this.request(path, req, responseType);
  }
  public <TReturn> TReturn request(String path, @Nullable Map<String, Object> request, Class<TReturn> responseType) throws IOException {
    if (request != null && request.containsKey("spaceName")) {
      final String spaceName = (String) request.get("spaceName");
      final String spaceId = spaceResolver.resolveSpaceId(this, spaceName);
      request.put("spaceId", spaceId);
    }
    final String url = this.resolveUrl(path, request);
    final Call call = this.createCallWithJsonBody(url, "GET", responseType);
    return executeCall(call, responseType);
  }

  public <U> U postStream(
      final String path, final File file, Map<String, Object> args, final Class<U> responseType)
      throws IOException {
    final RequestBody streamingBody = RequestBody.create(file, MediaType.parse("application/octet-stream"));
    final MultipartBody multipartBody = new MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("file", file.getName(), streamingBody)
        .build();
    final String url = this.resolveUrl(path, args);
    final Call call = createCall(url, "POST", multipartBody);
    return executeCall(call, responseType);
  }

  public <T, U> U put(
      final RequestEndpoint endpoint, final T bodyObject, final Class<U> responseType)
      throws IOException {
    final Call call = createCallWithJsonBody(endpoint, "PUT", bodyObject);
    return executeCall(call, responseType);
  }

  public void delete(final String path) throws IOException {
    delete(path, null);
  }

  public void delete(final String path, @Nullable Map<String, Object> args) throws IOException {
    if (args != null && args.containsKey("spaceName")) {
      String spaceId = spaceResolver.resolveSpaceId(this, (String) args.get("spaceName"));
      args.put("spaceId", spaceId);
    }
    String endpoint = resolveUrl(path, args);
    final Call call = createCall(endpoint, "DELETE", null);
    executeCall(call, Void.class);
  }

  private String generateUrl(final RequestEndpoint endpoint) {
    final HttpUrl httpUrl = HttpUrl.parse(serverUrl.toString());
    if (httpUrl == null) {
      throw new IllegalArgumentException("Unable to generate a HttpUrl from " + endpoint);
    }
    final HttpUrl.Builder builder = httpUrl.newBuilder();
    builder.addPathSegments(endpoint.getPath());
    endpoint
        .getQueryParameters()
        .forEach((k, v) -> v.forEach(i -> builder.addQueryParameter(k, i)));

    // There MAY be a double "/" in the URL (which is legal, but may cause issues
    // with some
    // frameworks)
    return builder.build().url().toString().replace("//", "/");
  }

  private <T> Call createCallWithJsonBody(
      final RequestEndpoint endpoint, final String method, final T bodyObject) {

    RequestBody body = null;
    if (bodyObject != null) {
      body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bodyObject));
    }
    return createCall(endpoint, method, body);
  }

  public ServerInformation getServerInformation() throws IOException {
    if (this.serverInformation == null) {
      final RootResource rootDocument = this.get(Constants.apiLocation, null, RootResource.class);
      if (rootDocument == null || rootDocument.Version == null) {
        throw new Error("he Octopus server information could not be retrieved. Please check the configured URL.");
      }
      this.serverInformation = new ServerInformation();
      this.serverInformation.version = rootDocument.Version;
      this.serverInformation.installationId = rootDocument.InstallationId;
    }
    return this.serverInformation;
  }

  private <T> Call createCallWithJsonBody(
          final String endpoint, final String method, final T bodyObject) {

    RequestBody body = null;
    if (bodyObject != null) {
      body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bodyObject));
    }
    return createCall(endpoint, method, body);
  }
  private Call createCall(final RequestEndpoint endpoint, final String method, final RequestBody body) {
    final String url = generateUrl(endpoint);
    final Request.Builder builder = new Request.Builder().method(method, body).url(url);
    requiredHeaders.forEach(
        (headerName, items) -> items.forEach(headerVal -> builder.addHeader(headerName, headerVal)));
    return httpClient.newCall(builder.build());
  }

  private Call createCall(final String url, final String method, final RequestBody body) {
    final Request.Builder builder = new Request.Builder().method(method, body).url(url);
    requiredHeaders.forEach(
            (headerName, items) -> items.forEach(headerVal -> builder.addHeader(headerName, headerVal)));
    return httpClient.newCall(builder.build());
  }

  private <T> T executeCall(final Call call, final Class<T> responseType) throws IOException {
    try (final Response response = call.execute()) {
      final ResponseBody body = response.body();
      if (body == null) {
        throw new IllegalStateException(
            "Response from " + call.request().url() + " contained no body");
      }
      final String responseBody = response.body().string();
      if (response.isSuccessful()) {
        try {
          return gson.fromJson(responseBody, responseType);
        } catch (final JsonSyntaxException e) {
          this.error(
                  String.format("Failed to decode the response body '%s' as a %s",
                          responseBody.toString(),
                          responseType.getSimpleName()),
                  null);
          throw e;
        }
      } else {
        throw constructException(response.code(), responseBody);
      }
    } catch (final UnknownHostException e) {
      this.error(String.format("Failed to connect to Octopus Server at %s", call.request().url().toString()), null);
      throw e;
    }
  }

  String resolveUrl(String path, @Nullable Map<String, Object> args) {
    return this.resolver.resolve(path, args);
  }

  private OctopusRequestException constructException(final int code, final String responseBody) {
    return new OctopusRequestException(code, responseBody);
  }

  public void info(String message) {
      this.logger.debug(message);
  }

  public void debug(String message) {
    this.logger.debug(message);
  }

  public void warn(String message) {
    this.logger.warn(message);
  }

  public void error(String message, @Nullable Exception error) {
    this.logger.error(message, error);
  }

  public Map<String, Object> convertObjToMap(Object obj) {
    Map<String, Object> map = new HashMap<>();
    Field[] allFields = obj.getClass().getDeclaredFields();
    for (Field field : allFields) {
      field.setAccessible(true);
      try {
        Object value = field.get(obj);
        map.put(field.getName(), value);
      } catch (IllegalAccessException e) {
        this.error("Can not convert object to map", e);
      }
    }
    return map;
  }
}
