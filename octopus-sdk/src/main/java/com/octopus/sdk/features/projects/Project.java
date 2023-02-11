package com.octopus.sdk.features.projects;

import com.octopus.sdk.NamedSpaceScopedResource;
import com.octopus.sdk.features.ExtensionSettingsValues;
import com.octopus.sdk.features.projects.deployment.processes.ActionTemplateParameter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Project extends NamedSpaceScopedResource {
    public String VariableSetId = "";
    public String DeploymentProcessId = "";
    public Boolean DiscreteChannelRelease = false;
    public List<String> IncludedLibraryVariableSetIds = new ArrayList<>();
    private String TenantedDeploymentMode = TenantedDeploymentModeType.TenantedOrUntenanted.name();
    public ReleaseCreationStrategy ReleaseCreationStrategy = new ReleaseCreationStrategy();
    public List<ActionTemplateParameter> Templates = new ArrayList<>();
    public List<Object> AutoDeployReleaseOverrides = new ArrayList<>();
    public String LifecycleId = "";
    public Boolean AutoCreateRelease = false;
    public String ClonedFromProjectId = "";
    public List<ExtensionSettingsValues> ExtensionSettings = new ArrayList<>();
    public Boolean IsVersionControlled = false;
    public Object PersistenceSettings;


    public TenantedDeploymentModeType getTenantedDeploymentMode() {
        return TenantedDeploymentModeType.valueOf(TenantedDeploymentMode);
    }

    public void setTenantedDeploymentMode(@NotNull() TenantedDeploymentModeType tenantedDeploymentMode) {
        TenantedDeploymentMode = tenantedDeploymentMode.name();
    }

    public void setPersistenceSettings(VersionControlledPersistenceSettings persistenceSettings) {
        PersistenceSettings = persistenceSettings;
    }

    public void setPersistenceSettings(DatabasePersistenceSettings persistenceSettings) {
        PersistenceSettings = persistenceSettings;
    }

    public String Slug = "";

    public String ProjectGroupId = "";
    public String Description = "";

    public Boolean IsDisabled = false;
}
