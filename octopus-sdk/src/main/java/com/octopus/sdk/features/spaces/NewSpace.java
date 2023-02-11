package com.octopus.sdk.features.spaces;

import com.octopus.sdk.NewNamedResource;

import javax.annotation.Nullable;
import java.util.Set;

public class NewSpace extends NewNamedResource {
    @Nullable()
    public String Slug;
    @Nullable()
    public String Description;
    public Boolean IsDefault;
    public Set<String> SpaceManagersTeams;
    public Set<String> SpaceManagersTeamMembers;
}
