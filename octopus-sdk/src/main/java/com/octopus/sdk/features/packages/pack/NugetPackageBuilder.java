package com.octopus.sdk.features.packages.pack;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NugetPackageBuilder {

    public String pack(NuGetPackArgs args) throws IOException {
        final String archiveFilename = String.format("%s.%s.nupkg", args.packageId, args.version);
        final List<String> inputFilePatterns = args.inputFilePatterns;
        if (args.nuspecArgs != null) {
            final String nuspecFilename = String.format("%s.nuspec");
            final Path nuspecFilePath = Paths.get(args.basePath, nuspecFilename);
            String nuspecFile = "";
            nuspecFile += "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            nuspecFile += "<package xmlns=\"http://schemas.microsoft.com/packaging/2010/07/nuspec.xsd\">\n";
            nuspecFile += "    <metadata>\n";
            nuspecFile += String.format("        <id>%s</id>\n", args.packageId);
            nuspecFile += String.format("        <version>%s</version>\n", args.version);
            nuspecFile += String.format("        <description>%s</description>\n", args.nuspecArgs.description);
            nuspecFile += String.format("        <authors>%s</authors>\n", String.join(",", args.nuspecArgs.authors));
            if (args.nuspecArgs.title != null && args.nuspecArgs.title != "") {
                nuspecFile += String.format("        <title>%s</title>\n", args.nuspecArgs.title);
            }
            if (args.nuspecArgs.releaseNotes != null && args.nuspecArgs.releaseNotes != "") {
                nuspecFile += String.format("        <releaseNotes></releaseNotes>\n", args.nuspecArgs.releaseNotes);
            }
            nuspecFile += "    </metadata>\n";
            nuspecFile += "</package>\n";

            Files.createFile(nuspecFilePath);
            Files.write(nuspecFilePath, nuspecFile.getBytes());

            inputFilePatterns.add(nuspecFilename);
        }
        ZipUtils.doZip(args.basePath, inputFilePatterns, args.outputFolder, archiveFilename, args.logger, 8, args.overwrite);
        return archiveFilename;
    }

    public class NuSpecArgs {
        public String description = "";
        public List<String> authors = new ArrayList<>();
        @Nullable
        public String title;
        @Nullable
        public String releaseNotes = "";
    }

    public class NuGetPackArgs extends PackArgs {
        @Nullable
        NuSpecArgs nuspecArgs;
    }
}
