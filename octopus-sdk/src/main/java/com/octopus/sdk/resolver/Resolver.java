package com.octopus.sdk.resolver;

import com.damnhandy.uri.template.UriTemplate;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Resolver {
  private String baseUri;
  private String rootUri;

  public Resolver(String baseUri) {
    this.baseUri = baseUri;
    this.baseUri = this.baseUri.endsWith("/") ? this.baseUri : this.baseUri + "/";

    final Integer lastIndexOfMandatorySegment = this.baseUri.lastIndexOf("/api/");
    if (lastIndexOfMandatorySegment >= 1) {
      this.baseUri = this.baseUri.substring(0, lastIndexOfMandatorySegment);
    } else {
      if (this.baseUri.endsWith("/api")) {
        this.baseUri = this.baseUri.substring(0, -4);
      }
    }

    this.baseUri = this.baseUri.endsWith("/") ? this.baseUri.substring(0, this.baseUri.length() - 1) : this.baseUri;

    final URI parsed = URI.create(this.baseUri);
    this.rootUri = parsed.getScheme() + "://" + parsed.getAuthority();
    this.rootUri = this.rootUri.endsWith("/") ? this.rootUri.substring(0, this.rootUri.length() - 1) : this.rootUri;
  }

  public String resolve(String path, @Nullable Map<String, Object> uriTemplateParameters) {
    if (path.length() == 0) {
      throw new Error("The link is not set to a vale");
    }

    if (path.startsWith("~/")) {
      path = path.substring(1, path.length());
      path = this.baseUri + path;
    } else {
      path = this.rootUri + path;
    }
    final UriTemplate template = UriTemplate.fromTemplate(path);
    if (uriTemplateParameters == null) {
      uriTemplateParameters = new HashMap<>();
    }
    final String result = template.expand(uriTemplateParameters);
    return result;
  }
}
