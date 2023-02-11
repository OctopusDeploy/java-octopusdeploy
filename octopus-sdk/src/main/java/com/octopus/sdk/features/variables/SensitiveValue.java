package com.octopus.sdk.features.variables;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SensitiveValue {
    @NotNull()
    public Boolean HasValue;
    @Nullable()
    public String Hint;
    @Nullable
    public String NewValue;

    public SensitiveValue() {
        this.HasValue = false;
    }

    public SensitiveValue(@NotNull() String value, @Nullable String hint) {
        this.HasValue = true;
        this.Hint = hint;
        this.NewValue = value;
    }
}
