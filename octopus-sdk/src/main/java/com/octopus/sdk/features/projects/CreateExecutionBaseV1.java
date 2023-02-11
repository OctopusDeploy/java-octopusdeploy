package com.octopus.sdk.features.projects;

import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class CreateExecutionBaseV1 extends SpaceScopedRequest {
    public String ProjectName = "";
    @Nullable
    public Boolean ForcePackageDownload;
    @Nullable
    public List<String> SpecificMachineNames;
    @Nullable
    public List<String> ExcludedMachineNames;
    @Nullable
    public List<String> SkipStepNames;
    @Nullable
    public Boolean UseGuidedFailure;
    @Nullable
    public Date RunAt;
    @Nullable
    public Date NoRunAfter;
    @Nullable
    public Map<String, String> Variables;
}
