package com.octopus.sdk.features;

import com.octopus.sdk.NewResource;
import com.octopus.sdk.Resource;
import com.octopus.sdk.ResourceCollection;
import com.octopus.sdk.http.OctopusClient;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Map;

abstract public class BasicRepository<
        TExistingResource extends Resource,
        TNewResource extends NewResource,
        TListArgs extends ListArgs
        > {
    public final Integer takeAll = 2147483647;
    public final Integer takeDefaultPageSize = 30;
    protected OctopusClient client;
    protected final String baseApiPathTemplate;
    protected final String listParametersTemplate;

    protected final Class<TExistingResource> existingResourceType;

    protected final Class<ResourceCollection<TExistingResource>> resourceCollectionType;

    public BasicRepository(
            OctopusClient client,
            String baseApiPathTemplate,
            String listParametersTemplate,
            Class<TExistingResource> existingResourceType,
            Class<ResourceCollection<TExistingResource>> resourceCollectionType
    ) {
        this.client = client;
        this.baseApiPathTemplate = baseApiPathTemplate;
        this.listParametersTemplate = listParametersTemplate;
        this.existingResourceType = existingResourceType;
        this.resourceCollectionType = resourceCollectionType;
    }

    public void delete(TExistingResource resource) throws IOException {
        this.client.delete(String.format("%s/%s", this.baseApiPathTemplate, resource.Id));
    }

    public TExistingResource create(TNewResource resource, @Nullable Map<String, Object> args) throws IOException {
        return this.client.doCreate(this.baseApiPathTemplate, resource, args, this.existingResourceType);
    }
    public TExistingResource create(TNewResource resource) throws IOException {
        return this.create(resource, null);
    }

    public TExistingResource get(String id) throws IOException {
        return this.client.get(String.format("%s/%s", this.baseApiPathTemplate, id), null, this.existingResourceType);
    }

    public ResourceCollection<TExistingResource> list(@Nullable TListArgs args) throws IOException {
        return this.client.request(String.format("%s{?%s}", this.baseApiPathTemplate, this.listParametersTemplate), args, this.resourceCollectionType);
    }

    public TExistingResource modify(TExistingResource resource, @Nullable Map<String, Object> args) throws IOException {
        return this.client.doUpdate(String.format("%s/%s", this.baseApiPathTemplate, resource.Id), resource, args, this.existingResourceType);
    }

    public TExistingResource modify(TExistingResource resource) throws IOException {
        return this.modify(resource, null);
    }

    public TExistingResource save(TExistingResource resource) throws IOException {
        return this.modify(resource);
    }

    public TExistingResource save(TNewResource resource) throws IOException {
        return this.create(resource);
    }
}
