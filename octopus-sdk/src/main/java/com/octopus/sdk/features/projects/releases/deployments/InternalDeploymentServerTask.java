package com.octopus.sdk.features.projects.releases.deployments;

// WARNING: we've had to do this to cover a mistake in Octopus' API. The API has been corrected to return PascalCase, but was returning camelCase
// for a number of versions, so we'll deserialize both and use whichever actually has a value
public class InternalDeploymentServerTask {
    public String DeploymentId = "";
    public String deploymentId = "";
    public String ServerTaskId = "";
    public String serverTaskId = "";
}
