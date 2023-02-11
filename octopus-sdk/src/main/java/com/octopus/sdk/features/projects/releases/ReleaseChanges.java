package com.octopus.sdk.features.projects.releases;

import com.octopus.sdk.features.projects.releases.buildinformation.CommitDetail;
import com.octopus.sdk.features.projects.releases.buildinformation.WorkItemLink;

import java.util.ArrayList;
import java.util.List;

public class ReleaseChanges {
    public String Version = "";
    public String ReleaseNotes = "";
    public List<WorkItemLink> WorkItems = new ArrayList<>();
    public List<CommitDetail> Commits = new ArrayList<>();
    public List<ReleasePackageVersionBuildInformation> BuildInformation = new ArrayList<>();
}
