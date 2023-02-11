package com.octopus.sdk.features.projects;

import com.octopus.sdk.NewSpaceScopedResource;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NewExecution extends NewSpaceScopedResource {
    public String ProjectId = "";
    public String EnvironmentId = "";
    public List<String> ExcludedMachineIds = new ArrayList<>();
    public Boolean ForcePackageDownload = true;
    public Map<String, Object> FormValues = new HashMap<>();
    public String Comments = "";
    @Nullable
    public Date QueueTime;
    @Nullable
    public Date QueueTimeExpiry;
    public List<String> SkipActions = new ArrayList<>();
    public List<String> SpecificMachineIds = new ArrayList<>();
    @Nullable
    public String TenantId;
    public Boolean UseGuidedFailure;
}
