package com.octopus.sdk.features.projects.releases.deployments;

import com.octopus.sdk.features.projects.CreateExecutionBaseV1;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CreateDeploymentTenantedCommandV1 extends CreateExecutionBaseV1 {
    public String spaceName = "";
    public String spaceIdOrName = "";
    public String ReleaseVersion = "";
    public String EnvironmentName = "";
    public List<String> Tenants = new ArrayList<>();
    public List<String> TenantTags = new ArrayList<>();
    @Nullable
    public Boolean ForcePackageRedeployment;
    @Nullable
    public Boolean UpdateVariableSnapshot;
}
