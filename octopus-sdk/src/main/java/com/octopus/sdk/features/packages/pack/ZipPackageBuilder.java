package com.octopus.sdk.features.packages.pack;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ZipPackageBuilder {

    public String pack(ZipPackArgs args) throws IOException {
        final String archiveFilename = String.format("%s.%s.zip", args.packageId, args.version);
        ZipUtils.doZip(args.basePath, args.inputFilePatterns, args.outputFolder, archiveFilename, args.logger, args.compressionLevel, args.overwrite);
        return archiveFilename;
    }

    public class ZipPackArgs extends PackArgs {
        @Nullable
        public int compressionLevel;
    }
}
