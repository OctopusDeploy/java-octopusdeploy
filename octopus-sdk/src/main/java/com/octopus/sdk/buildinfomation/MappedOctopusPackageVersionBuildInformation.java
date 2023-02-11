package com.octopus.sdk.buildinfomation;

import com.octopus.sdk.Resource;
import com.octopus.sdk.features.projects.releases.buildinformation.CommitDetail;
import com.octopus.sdk.features.projects.releases.buildinformation.WorkItemLink;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MappedOctopusPackageVersionBuildInformation extends Resource {
    public String Branch = "";
    public String BuildNumber = "";
    public String BuildUrl = "";
    public List<CommitDetail> Commits = new ArrayList<>();
    @Nullable
    public String Created;
    public String Id = "";
    public String IncompleteDataWarning = "";
    public String IssueTrackerId = "";
    public String PackageId = "";
    public String VcsCommitUrl = "";
    public String VcsType = "";
    public String VcsRoot = "";
    public String Version = "";
    public List<WorkItemLink> WorkItems = new ArrayList<>();
}
