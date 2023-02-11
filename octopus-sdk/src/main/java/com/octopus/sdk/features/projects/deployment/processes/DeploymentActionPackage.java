package com.octopus.sdk.features.projects.deployment.processes;

import org.jetbrains.annotations.Nullable;

public class DeploymentActionPackage {
    public String DeploymentAction = "";
    @Nullable()
    public String PackageReference;

    public String displayName() {
        if (this.PackageReference != null) {
            return String.format("%s/%s", this.DeploymentAction, this.PackageReference);
        }
        return this.DeploymentAction;
    }
}
