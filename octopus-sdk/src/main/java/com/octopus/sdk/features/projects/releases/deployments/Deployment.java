package com.octopus.sdk.features.projects.releases.deployments;

import com.octopus.sdk.features.projects.Execution;
import com.octopus.sdk.features.projects.releases.ReleaseChanges;

import java.util.ArrayList;
import java.util.List;

public class Deployment extends Execution {
    public String ReleaseId = "";
    public List<ReleaseChanges> Changes = new ArrayList<>();
    public String ChangesMarkdown = "";
    public String DeploymentProcessId = "";
    public String ChannelId;
    public Boolean ForcePackageRedeployment = false;
}
