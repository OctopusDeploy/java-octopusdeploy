package com.octopus.sdk.features.projects;

import com.octopus.sdk.SpaceScopedResource;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Execution extends SpaceScopedResource {
    public String Name = "";
    public String Comments = "";
    public String Created = "";
    public String EnvironmentId = "";
    public List<String> ExcludeMachineIds = new ArrayList<>();
    public Boolean ForcePackageDownload = false;
    public Map<String, Object> FormValues = new HashMap<>();
    public String ManifestVariableSetId = "";
    public String ProjectId = "";
    @Nullable
    public Date QueueTime;
    @Nullable
    public Date QueueTimeExpiry;
    public List<String> SkipActions = new ArrayList<>();
    public List<String> SpecificMachineIds = new ArrayList<>();
    public String TaskId = "";
    @Nullable
    public String TenantId = "";
    public Boolean UseGuidedFailure = false;
}
