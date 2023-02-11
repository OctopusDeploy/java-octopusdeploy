package com.octopus.sdk.features.packages.pack;

import com.octopus.sdk.logging.Logger;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ZipUtils {
    public static void doZip(
            final String basePath,
            final List<String> inputFilePatterns,
            final String outputFolder,
            final String zipFilename,
            final Logger logger,
            @Nullable
            int compressionLevel,
            @Nullable
            boolean overwrite
    ) throws IOException {
        final Path archivePath = Paths.get(outputFolder, zipFilename);
        logger.info(String.format("Writing to package: %s...", archivePath));

        final String initialWorkingDirectory = System.getProperty("user.dir");
        final Path location = Paths.get(initialWorkingDirectory, basePath);

        final ZipFile zip = new ZipFile(zipFilename.toString());

        final List<Path> files = expandGlobs(inputFilePatterns, location);
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setIncludeRootFolder(true);
        zipParameters.setSymbolicLinkAction(ZipParameters.SymbolicLinkAction.INCLUDE_LINK_AND_LINKED_FILE);
        if (compressionLevel > 0) {
            zipParameters.setCompressionLevel(getCompressionLevel(compressionLevel));
        }
        for (Path file : files) {
            logger.debug(String.format("Adding file: %s", file.toString()));
            zip.addFile(file.toString(), zipParameters);
        }
    }

    private static CompressionLevel getCompressionLevel(int compressionLevel) {
        if (compressionLevel > -1 && compressionLevel < 10) {
            return CompressionLevel.values()[compressionLevel];
        }
        return CompressionLevel.FAST;
    }

    private static List<Path> expandGlobs(List<String> filePatterns, Path location) throws IOException {
        final List<Path> files = new ArrayList<>();

        for (String filePattern : filePatterns) {
            for (String fileName : filePattern.split(",")) {
                final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(fileName);
                Files.walkFileTree(location, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                        if (pathMatcher.matches(path)) {
                            files.add(path);
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        }
        return files;
    }
}
