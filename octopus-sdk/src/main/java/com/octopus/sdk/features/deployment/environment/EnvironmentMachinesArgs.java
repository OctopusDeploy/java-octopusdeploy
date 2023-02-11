package com.octopus.sdk.features.deployment.environment;

import org.jetbrains.annotations.Nullable;

public class EnvironmentMachinesArgs {
    public int skip = 0;
    public int take = 0;
    @Nullable
    public String partialName;
    @Nullable
    public String roles;
    public boolean isDisabled = false;
    @Nullable
    public String healthStatuses;
    @Nullable
    public String commStyles;
    @Nullable
    public String tenantTags;
    @Nullable
    public String shellNames;
}
