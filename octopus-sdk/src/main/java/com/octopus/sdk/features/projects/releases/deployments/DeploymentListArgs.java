package com.octopus.sdk.features.projects.releases.deployments;

import com.octopus.sdk.features.servertasks.TaskState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeploymentListArgs {
    @Nullable
    public String spaceIdOrName;
    @Nullable
    public String spaceName;
    @Nullable
    public List<String> ids;
    @Nullable
    public List<String> projects;
    @Nullable
    public List<String> tenants;
    @Nullable
    public List<String> channels;
    private String taskState;

    public TaskState getTaskState() {
        return TaskState.valueOf(taskState);
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState.name();
    }
}
