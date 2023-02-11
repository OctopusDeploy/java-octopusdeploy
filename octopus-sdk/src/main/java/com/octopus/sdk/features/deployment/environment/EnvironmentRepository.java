package com.octopus.sdk.features.deployment.environment;

import com.octopus.sdk.Constants;
import com.octopus.sdk.ResourceCollection;
import com.octopus.sdk.features.SpaceScopedBasicRepository;
import com.octopus.sdk.http.OctopusClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvironmentRepository extends SpaceScopedBasicRepository<DeploymentEnvironment, NewDeploymentEnvironment, EnvironmentRepositoryListArgs> {

    public EnvironmentRepository(OctopusClient client, String spaceName) throws Exception {
        super(
                client,
                spaceName,
                String.format("%s/environments", Constants.spaceScopedRoutePrefix),
                "skip,take,ids,partialName",
                DeploymentEnvironment.class,
                (Class<ResourceCollection<DeploymentEnvironment>>) new ResourceCollection<DeploymentEnvironment>().getClass()
        );
    }

    public DeploymentEnvironment sort(List<String> order) throws IOException {
        Map<String, Object> args = new HashMap<>();
        args.put("spaceName", this.spaceName);
        return this.client.doUpdate(String.format("%s/sortorder", this.baseApiPathTemplate), order, args, DeploymentEnvironment.class);
    }
}
