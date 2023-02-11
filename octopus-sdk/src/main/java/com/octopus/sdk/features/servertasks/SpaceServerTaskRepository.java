package com.octopus.sdk.features.servertasks;

import com.octopus.sdk.Constants;
import com.octopus.sdk.ResourceCollection;
import com.octopus.sdk.SpaceName;
import com.octopus.sdk.SpaceNameCollection;
import com.octopus.sdk.http.OctopusClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpaceServerTaskRepository {
    private String baseApiPathTemplate = String.format("%s/tasks", Constants.spaceScopedRoutePrefix);
    private final OctopusClient client;
    private final String spaceName;

    public SpaceServerTaskRepository(OctopusClient client, String spaceName) {
        this.client = client;
        this.spaceName = spaceName;
    }

    public ServerTask getById(String serverTaskId) throws IOException {
        if (serverTaskId == null) {
            throw new IOException("Server Task Id was not Provided");
        }
        final SpaceName args = new SpaceName(spaceName);
        final ServerTask response = this.client.request(String.format("%s/%s", this.baseApiPathTemplate, serverTaskId), spaceName, ServerTask.class);
        return response;
    }

    public List<ServerTask> getByIds(List<String> serverTaskIds) throws IOException {
        String[] taskIdsArray = new String[serverTaskIds.size()];
        serverTaskIds.toArray(taskIdsArray);
        final int batchSize = 300;
        final List<String[]> idArrays = new ArrayList<>();
        for (int i = 0; i < serverTaskIds.size() - batchSize + 1; i += batchSize) {
            idArrays.add(Arrays.copyOfRange(taskIdsArray, i, i + batchSize));
        }
        if (serverTaskIds.size() % batchSize != 0) {
            idArrays.add(Arrays.copyOfRange(taskIdsArray, serverTaskIds.size() - serverTaskIds.size() % batchSize, serverTaskIds.size()));
        }
        List<ResourceCollection<ServerTask>> responses = new ArrayList<>();
        ResourceCollection<ServerTask> response = new ResourceCollection<>();
        int index = 0;
        for (String[] ids : idArrays) {
            final SpaceNameCollection args = new SpaceNameCollection(this.spaceName, ids, index * batchSize, batchSize);
            response = this.client.request(String.format("%s/{?skip,take,ids,partialName}", this.baseApiPathTemplate), args, response.getClass());
            responses.add(response);
            index++;
        }
        List<ServerTask> serverTasks = new ArrayList<>();
        for (ResourceCollection<ServerTask> c : responses) {
           for (ServerTask serverTask : c.Items) {
               serverTasks.add(serverTask);
           }
        }
        return serverTasks;
    }
}
