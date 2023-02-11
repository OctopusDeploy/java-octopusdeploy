package com.octopus.sdk.features;

import com.octopus.sdk.*;
import com.octopus.sdk.http.OctopusClient;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpaceScopedBasicRepository<
        TExistingResource extends SpaceScopedResource,
        TNewResource extends NewSpaceScopedResource,
        TListArgs extends ListArgs
        > extends BasicRepository<TExistingResource, TNewResource, TListArgs> {
    protected final String spaceName;

    public SpaceScopedBasicRepository(
            OctopusClient client,
            String spaceName,
            String baseApiPathTemplate,
            String listParametersTemplate,
            Class<TExistingResource> existingResourceType,
            Class<ResourceCollection<TExistingResource>> resourceCollectionType
    ) throws Exception {
        super(client, baseApiPathTemplate, listParametersTemplate, existingResourceType, resourceCollectionType);
        if (!baseApiPathTemplate.startsWith(Constants.spaceScopedRoutePrefix)) {
            throw new Exception(String.format("Space scoped repositories must prefix their baseApiTemplate with %s", Constants.spaceScopedRoutePrefix));
        }
        this.spaceName = spaceName;
    }

    @Override
    public void delete(TExistingResource resource) throws IOException {
        final Map<String, Object> args = new HashMap<>();
        args.put("spaceName", this.spaceName);
        this.client.delete(String.format("%s/%s", this.baseApiPathTemplate, resource.Id), args);
    }

    @Override
    public TExistingResource create(TNewResource resource, @Nullable Map<String, Object> args) throws IOException {
        if (args == null) {
            args = new HashMap<>();
        }
        args.put("spaceName", this.spaceName);
        return this.client.doCreate(this.baseApiPathTemplate, resource, args, this.existingResourceType);
    }

    @Override
    public TExistingResource create(TNewResource resource) throws IOException {
        return this.create(resource, null);
    }

    @Override
    public TExistingResource get(String id) throws IOException {
        Map<String, Object> args = new HashMap<>();
        args.put("spaceName", spaceName);
        return this.client.get(String.format("%s/%s", this.baseApiPathTemplate, id), args, this.existingResourceType);
    }

    @Override
    public ResourceCollection<TExistingResource> list(@Nullable TListArgs args) throws IOException {
        Map<String, Object> spacedArgs = null;
        if (args != null) {
            spacedArgs = this.client.convertObjToMap(args);
            spacedArgs.put("spaceName", this.spaceName);
        }
        return this.client.request(String.format("%s{?%s}", this.baseApiPathTemplate, this.listParametersTemplate), spacedArgs, this.resourceCollectionType);
    }
}
