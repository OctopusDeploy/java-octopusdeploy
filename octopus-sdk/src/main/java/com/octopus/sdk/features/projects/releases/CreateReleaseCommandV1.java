package com.octopus.sdk.features.projects.releases;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CreateReleaseCommandV1 {
    public String ProjectName = "";
    public String spaceIdOrName = "";
    public String spaceName = "";
    @Nullable()
    public String PackageVersion;
    @Nullable()
    public String GitCommit;
    @Nullable()
    public String GitRef;
    @Nullable()
    public String ReleaseVersion;
    @Nullable()
    public String ChannelName;
    @Nullable()
    public List<String> Packages;
    @Nullable()
    public String ReleaseNotes;
    @Nullable()
    public Boolean IgnoreIfAlreadyExists;
    @Nullable()
    public Boolean IgnoreChannelRules;
    @Nullable()
    public String PackagePrerelease;
}
