package com.octopus.sdk.features.packages;

import com.octopus.sdk.Constants;
import com.octopus.sdk.ResourceCollection;
import com.octopus.sdk.SpaceName;
import com.octopus.sdk.features.OverwriteMode;
import com.octopus.sdk.http.OctopusClient;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageRepository {
    private OctopusClient client;
    private String spaceName;

    PackageRepository(OctopusClient client, String spaceName) {
        this.client = client;
        this.spaceName = spaceName;
    }

    public Package get(String packageId) throws Exception {
        if (packageId == null || packageId.length() == 0) {
            throw new Exception("Package Id was not Provided");
        }
        final PackageRequest packageRequest = new PackageRequest(this.spaceName, packageId);
        final Package response = this.client.request(
                String.format("%s/packages{/packageId}", Constants.spaceScopedRoutePrefix),
                packageRequest,
                Package.class
        );
        return response;
    }

    public ResourceCollection<Package> list(@Nullable PackageListArgs args) throws IOException {
        ResourceCollection<Package> response = new ResourceCollection<>();
        args.spaceName = this.spaceName;
        response = this.client.request(
                String.format("%s/packages{/id}{?nuGetPackageId,filter,latest,skip,take,includeNotes}", Constants.spaceScopedRoutePrefix),
                args,
                response.getClass()
        );
        return response;
    }

    public void push(String[] packages) throws IOException {
        this.push(packages, OverwriteMode.FailIfExists);
    }

    public void push(String[] packages, OverwriteMode overwriteMode) throws IOException {
        final String spaceId = this.client.spaceResolver.resolveSpaceId(this.client, this.spaceName);

        List<IOException> failedTasks = new ArrayList<>();
        for (String packagePath : packages) {
            try {
                this.packageUpload(spaceId, packagePath, overwriteMode);
            } catch (IOException e) {
                failedTasks.add(e);
            }
        }

        if (failedTasks.size() > 0) {
            String messages = "";
            for (IOException e : failedTasks) {
                messages += e.getMessage() + "\n";
            }
            throw new IOException(messages);
        }
        this.client.info("Packages uploaded");
    }

    private void packageUpload(String spaceId, String filePath, OverwriteMode overwriteMode) throws IOException {
        final File file = new File(filePath);
        this.client.info(String.format("Uploading package, %s", file.getName()));
        this.upload(spaceId, file, overwriteMode);
    }

    private Package upload(String spaceId, File file, OverwriteMode overwriteMode) throws IOException {
        final Map<String, Object> args = new HashMap<>();
        args.put("overwriteMode", overwriteMode.toString());
        args.put("spaceId", spaceId);
        return this.client.postStream(
                String.format("%s/packages/raw{?overwriteMode}", Constants.spaceScopedRoutePrefix),
                file,
                args,
                Package.class
        );
    }

    private class PackageRequest extends SpaceName {
        public String packageId;
        public PackageRequest(String spaceName, String packageId) {
            super(spaceName);
            this.packageId = packageId;
        }
    }
}
