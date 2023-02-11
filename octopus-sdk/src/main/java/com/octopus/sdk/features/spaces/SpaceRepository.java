package com.octopus.sdk.features.spaces;

import com.octopus.sdk.Constants;
import com.octopus.sdk.features.BasicRepository;
import com.octopus.sdk.http.OctopusClient;

public class SpaceRepository extends BasicRepository<Space, NewSpace, SpaceRepositoryListArgs> {
    public SpaceRepository(OctopusClient client) {
        super(client, String.format("%s/spaces", Constants.apiLocation), "skip,ids,take,partialName");
    }
}
