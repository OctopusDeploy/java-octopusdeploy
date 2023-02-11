package com.octopus.sdk.features.servertasks;

import com.octopus.sdk.NamedResource;
import org.jetbrains.annotations.Nullable;

public class ServerTask extends NamedResource {
    public String Description = "";
    public TaskState askState;
    @Nullable
    public String Completed;
    @Nullable
    public String QueueTime = "";
    @Nullable
    public String QueueTimeExpiry;
    @Nullable
    public String StartTime;
    @Nullable
    public String LastUpdatedTime;
    @Nullable
    public String CompletedTime;
    @Nullable
    public String ServerNode;
    @Nullable
    public String Duration;
    @Nullable
    public String ErrorMessage;
    @Nullable
    public Boolean HasBeenPickedUpByProcessor;
    public Boolean IsCompleted = false;
    @Nullable
    public Boolean FinishedSuccessfully;
    public Boolean HasPendingInterruptions = false;
    @Nullable
    public Boolean CanRerun;
    public Boolean HasWarningsOrErrors = false;
}
