package com.octopus.sdk.resolver;

import com.octopus.sdk.Constants;
import com.octopus.sdk.ResourceCollection;
import com.octopus.sdk.features.spaces.Space;
import com.octopus.sdk.http.OctopusClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpaceResolver {
    private Map<String, String> knownSpaces = new HashMap();

    public String resolveSpaceId(OctopusClient client, String spaceName) throws IOException {
        if (this.knownSpaces.containsKey(spaceName)) {
            return this.knownSpaces.get(spaceName);
        }
        client.debug(String.format("Resolving space from name '%s", spaceName));
        Map<String, Object> args = new HashMap<>();
        args.put("partialName", spaceName);
        ResourceCollection<Space> spaces = new ResourceCollection<>();
        spaces = client.get(String.format("%s/spaces", Constants.apiLocation), args, spaces.getClass());
        String spaceId = "";

        if (spaces.TotalResults == 0)  {
            final String msg = String.format("No spaces exist with name '%s'", spaceName);
            client.error(msg, null);
            throw new Error(msg);
        }

        for (Space space : spaces.Items) {
            if (space.Name == spaceName) {
                spaceId = space.Id;
                knownSpaces.put(spaceName, spaceId);
                client.debug(String.format("Resolved space name '%s', to Id %s", spaceName, spaceId));
            }
        }

        if (spaceId == "") {
            String msg = String.format("Unable to resolve space name '%s'", spaceName);
            client.error(String.format("Unable to resolve space name '%s'", spaceName), null);
            throw new Error(msg);
        }

        return spaceId;
    }
}
