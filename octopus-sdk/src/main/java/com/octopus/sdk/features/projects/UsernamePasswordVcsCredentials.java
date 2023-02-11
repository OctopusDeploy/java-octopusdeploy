package com.octopus.sdk.features.projects;

import com.octopus.sdk.features.variables.SensitiveValue;
import org.jetbrains.annotations.NotNull;

public class UsernamePasswordVcsCredentials {
    private static final String Type = AuthenticationType.UsernamePassword.name();
    @NotNull()
    public String Username = "";

    @NotNull()
    public SensitiveValue Password = new SensitiveValue();

    public static AuthenticationType getType() {
        return AuthenticationType.UsernamePassword;
    }
}
