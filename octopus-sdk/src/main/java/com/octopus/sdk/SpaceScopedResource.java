package com.octopus.sdk;

import org.jetbrains.annotations.NotNull;

abstract public class SpaceScopedResource extends Resource {
    @NotNull()
    public String SpaceId = "";
    @NotNull()
    public String Name = "";
}