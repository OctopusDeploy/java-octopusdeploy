package com.octopus.sdk.features.projects.releases;

import com.octopus.sdk.Constants;
import com.octopus.sdk.http.OctopusClient;

import java.io.IOException;

public class ReleaseRepository {
    private OctopusClient client;
    private String spaceName;

    public ReleaseRepository(OctopusClient client, String spaceName) {
        this.client = client;
        this.spaceName = spaceName;
    }

    public CreateReleaseResponseV1 create(CreateReleaseCommandV1 command) throws IOException {
        this.client.debug("Create a release...");

        command.spaceIdOrName = command.spaceName;
        final CreateReleaseResponseV1 response = this.client.doCreate(String.format("%s/release/create/v1", Constants.spaceScopedRoutePrefix), command, null, CreateReleaseResponseV1.class);

        this.client.debug("Release created successfully");

        return response;
    }
}
