package com.octopus.sdk.logging;

import org.jetbrains.annotations.Nullable;

public interface Logger {
     void info(String message);
    void debug(String message);
    void warn(String message);
    void error(String message, @Nullable Exception error);
}
