package com.octopus.sdk.features.projects;

public class AnonymousVcsCredentials {
    private static final String Type = AuthenticationType.Anonymous.name();

    public static AuthenticationType getType() {
        return AuthenticationType.Anonymous;
    }
}
