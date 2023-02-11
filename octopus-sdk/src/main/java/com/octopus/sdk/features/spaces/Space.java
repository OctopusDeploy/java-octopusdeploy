package com.octopus.sdk.features.spaces;

import com.octopus.sdk.NamedResource;

import javax.annotation.Nullable;
import java.util.Set;

public class Space extends NamedResource {
    public String Slug;
    @Nullable()
    public String Description;
    public Boolean IsDefault;
    public Set<String> SpaceManagersTeamMembers;
}
