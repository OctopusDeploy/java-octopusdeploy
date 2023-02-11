package com.octopus.sdk.features.projects;

import org.jetbrains.annotations.NotNull;

public class VersionControlledPersistenceSettings {
    private static final String Type = PersistenceSettingsType.VersionControlled.name();
    private Object Credentials;
    @NotNull()
    public String Url = "";
    @NotNull()
    public String DefaultBranch = "";
    @NotNull()
    public String BasePath = "";
    
    public static PersistenceSettingsType getType() {
        return PersistenceSettingsType.VersionControlled;
    }

    public void setCredentials(AnonymousVcsCredentials credentials) {
        Credentials = credentials;
    }

    public void setCredentials(UsernamePasswordVcsCredentials credentials) {
        Credentials = credentials;
    }
}
