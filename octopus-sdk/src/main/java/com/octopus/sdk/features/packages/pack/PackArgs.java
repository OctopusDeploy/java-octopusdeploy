package com.octopus.sdk.features.packages.pack;

import com.octopus.sdk.logging.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PackArgs {
    public String packageId = "";
    public String version = "";
    public String basePath = "";
    public List<String> inputFilePatterns = new ArrayList<>();
    public String outputFolder = "";
    @Nullable
    public Boolean overwrite;
    public Logger logger;
}
