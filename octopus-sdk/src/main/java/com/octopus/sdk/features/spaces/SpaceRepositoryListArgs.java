package com.octopus.sdk.features.spaces;

import com.octopus.sdk.features.ListArgs;

import javax.annotation.Nullable;
import java.util.List;

public class SpaceRepositoryListArgs extends ListArgs {
    @Nullable()
    public List<String> ids;
    @Nullable()
    public String partialName;
}
