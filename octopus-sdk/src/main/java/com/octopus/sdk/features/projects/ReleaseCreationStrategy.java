package com.octopus.sdk.features.projects;

import com.octopus.sdk.features.projects.deployment.processes.DeploymentActionPackage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReleaseCreationStrategy {
    public DeploymentActionPackage ReleaseCreationPackage = new DeploymentActionPackage();
    @Nullable()
    public String ChannelId;
    @Nullable()
    public String ReleaseCreationPackageStepId;
}
