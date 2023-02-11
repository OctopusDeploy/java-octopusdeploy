package com.octopus.sdk.features.projects.releases.deployments;

import com.octopus.sdk.features.projects.CreateExecutionBaseV1;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CreateDeploymentUntenantedCommandV1 extends CreateExecutionBaseV1 {
    public String SpaceName = "";
    public String spaceIdOrName = "";
    public String ReleaseVersion = "";
    public List<String> EnvironmentNames = new ArrayList<>();
    @Nullable
    public Boolean ForcePackageRedeployment;
    @Nullable
    public Boolean UpdateVariableSnapshot;
}
