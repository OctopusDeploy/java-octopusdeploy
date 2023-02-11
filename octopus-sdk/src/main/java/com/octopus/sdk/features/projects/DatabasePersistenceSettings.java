package com.octopus.sdk.features.projects;

public class DatabasePersistenceSettings {
    private static final String Type = PersistenceSettingsType.Database.name();

    public static PersistenceSettingsType getType() {
        return PersistenceSettingsType.Database;
    }
}
