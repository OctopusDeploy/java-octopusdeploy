package com.octopus.sdk.features.projects.releases.deployments;

import com.octopus.sdk.Constants;
import com.octopus.sdk.ResourceCollection;
import com.octopus.sdk.SpaceName;
import com.octopus.sdk.http.OctopusClient;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeploymentRepository {
    private String baseApiPathTemplate = String.format("%s/deployments", Constants.spaceScopedRoutePrefix);

    private OctopusClient client;

    private final String spaceName;

    public DeploymentRepository(OctopusClient client, String spaceName) {
        this.client = client;
        this.spaceName = spaceName;
    }

    public Deployment get(String id) throws IOException {
        return this.client.request(String.format("%s/%s", this.baseApiPathTemplate, id), new SpaceName(this.spaceName), Deployment.class);
    }

    public ResourceCollection<Deployment> list(@Nullable DeploymentListArgs args) throws IOException {
        ResourceCollection<Deployment> response = new ResourceCollection<>();
        response = this.client.request(String.format("%s{?skip,take,ids,projects,environments,tenants,channels,taskState}", this.baseApiPathTemplate), args, response.getClass());
        return response;
    }

    public CreateDeploymentUntenantedResponseV1 create(CreateDeploymentUntenantedCommandV1 command) throws IOException {
        this.client.debug("Deploying a release...");
        command.spaceIdOrName = command.SpaceName;
        final InternalClassDeploymentResponseV1 response = this.client.doCreate(String.format("%s/create/untenanted/v1", this.baseApiPathTemplate), command, null, InternalClassDeploymentResponseV1.class);

        if (response.DeploymentServerTasks.size() == 0) {
            throw new Error("No server task details returned");
        }

        final List<DeploymentServerTask> mappedTasks = new ArrayList<>();
        final List<String> serverTaskIds = new ArrayList<>();
        for (InternalDeploymentServerTask task : response.DeploymentServerTasks) {
            DeploymentServerTask newTask = new DeploymentServerTask();
            newTask.DeploymentId = task.DeploymentId == "" ? task.deploymentId : task.DeploymentId;
            newTask.ServerTaskId = task.ServerTaskId == "" ? task.serverTaskId : task.ServerTaskId;
            mappedTasks.add(newTask);
            serverTaskIds.add(newTask.ServerTaskId);
        }

        this.client.debug(String.format("Deployment(s) create successfully. [%s]", String.join(",", serverTaskIds)));
        final CreateDeploymentUntenantedResponseV1 result = new CreateDeploymentUntenantedResponseV1();
        result.DeploymentServerTask = mappedTasks;
        return result;
    }
    public CreateDeploymentTenantedResponseV1 createTenanted(CreateDeploymentTenantedCommandV1 command) throws IOException {
        this.client.debug("Deploying a tenanted release...");
        command.spaceIdOrName = command.spaceName;
        final InternalClassDeploymentResponseV1 response = this.client.doCreate(String.format("%s/create/tenanted/v1", this.baseApiPathTemplate), command, null, InternalClassDeploymentResponseV1.class);

        if (response.DeploymentServerTasks.size() == 0) {
            throw new Error("No server task details returned");
        }

        final List<DeploymentServerTask> mappedTasks = new ArrayList<>();
        final List<String> serverTaskIds = new ArrayList<>();
        for (InternalDeploymentServerTask task : response.DeploymentServerTasks) {
            DeploymentServerTask newTask = new DeploymentServerTask();
            newTask.DeploymentId = task.DeploymentId == "" ? task.deploymentId : task.DeploymentId;
            newTask.ServerTaskId = task.ServerTaskId == "" ? task.serverTaskId : task.ServerTaskId;
            mappedTasks.add(newTask);
            serverTaskIds.add(newTask.ServerTaskId);
        }

        this.client.debug(String.format("Tenanted Deployment(s) create successfully. [%s]", String.join(",", serverTaskIds)));
        final CreateDeploymentTenantedResponseV1 result = new CreateDeploymentTenantedResponseV1();
        result.DeploymentServerTasks = mappedTasks;
        return result;
    }
}
