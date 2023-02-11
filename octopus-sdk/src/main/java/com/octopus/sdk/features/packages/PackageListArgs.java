package com.octopus.sdk.features.packages;

import com.octopus.sdk.features.ListArgs;
import org.jetbrains.annotations.Nullable;

public class PackageListArgs extends ListArgs {
    @Nullable
    public String nuGetPackageId;
    @Nullable
    public String filter;
    @Nullable
    public Boolean latest;
    @Nullable
    public Boolean includeNotes;
    public String spaceName;
}
