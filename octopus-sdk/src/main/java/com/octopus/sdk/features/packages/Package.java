package com.octopus.sdk.features.packages;

import com.octopus.sdk.Resource;
import com.octopus.sdk.buildinfomation.MappedOctopusPackageVersionBuildInformation;
import org.jetbrains.annotations.Nullable;

public class Package extends Resource {
    public String Description = "";
    public String FeedId = "";
    public String FileExtension = "";
    public String PackageId = "";
    @Nullable
    public MappedOctopusPackageVersionBuildInformation PackageVersionBuildInformation;
    public String Published = "";
    public String ReleaseNotes = "";
    public String Summary = "";
    public String Title = "";
    public String Version = "";
}
