package com.octopus.sdk.features.deployment.environment;

import com.octopus.sdk.features.ListArgs;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnvironmentRepositoryListArgs extends ListArgs {
    @Nullable
    public List<String> ids;
    @Nullable
    public String partialName;
}
