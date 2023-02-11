package com.octopus.sdk.features.deployment.environment;

import com.octopus.sdk.NamedResource;
import com.octopus.sdk.NamedSpaceScopedResource;
import com.octopus.sdk.SpaceScopedResource;
import com.octopus.sdk.features.ExtensionSettingsValues;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DeploymentEnvironment extends NamedSpaceScopedResource {
    @Nullable
    public String Description;
    public Boolean AllowDynamicInfrastructure = false;
    public List<ExtensionSettingsValues> ExtensionSettings = new ArrayList<>();
    public int SortOrder = 0;
    public boolean UseGuidedFailure = false;
}
