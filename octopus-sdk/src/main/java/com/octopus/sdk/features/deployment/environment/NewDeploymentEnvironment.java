package com.octopus.sdk.features.deployment.environment;

import com.octopus.sdk.NewNamedSpaceScopedResource;
import com.octopus.sdk.NewSpaceScopedResource;
import com.octopus.sdk.features.ExtensionSettingsValues;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NewDeploymentEnvironment extends NewSpaceScopedResource {
    @Nullable
    public String Description;
    @Nullable
    public Boolean UseGuidedFailure;
    @Nullable
    public Boolean AllowDynamicInfrastructure;
    @Nullable
    public int SortOrder;
    @Nullable
    public List<ExtensionSettingsValues> ExtensionSettings;
}
