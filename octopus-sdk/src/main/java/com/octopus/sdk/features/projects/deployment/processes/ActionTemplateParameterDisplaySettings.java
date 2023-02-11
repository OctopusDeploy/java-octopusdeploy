package com.octopus.sdk.features.projects.deployment.processes;

import com.google.gson.annotations.SerializedName;
import com.octopus.sdk.features.forms.ControlType;
import org.jetbrains.annotations.Nullable;

public class ActionTemplateParameterDisplaySettings {
    @SerializedName("Octopus.ControlType")
    @Nullable()
    private String controlType;
    @SerializedName("Octopus.SelectOptions")
    @Nullable()
    public String SelectOptions;

    public void setControlType(@Nullable ControlType controlType) {
        this.controlType = controlType.name();
    }

    @Nullable
    public ControlType getControlType() {
        if (this.controlType == null) {
            return null;
        }
        return ControlType.valueOf(this.controlType);
    }
}
