package com.octopus.sdk.features.projects.deployment.processes;

import com.octopus.sdk.Resource;
import com.octopus.sdk.features.variables.SensitiveValue;
import org.jetbrains.annotations.Nullable;

public class ActionTemplateParameter extends Resource {
    @Nullable()
    public boolean AllowClear;
    @Nullable()
    private Object DefaultValue;
    public ActionTemplateParameterDisplaySettings DisplaySettings = new ActionTemplateParameterDisplaySettings();
    public String HelpText = "";
    public String Label = "";
    public String Name = "";

    public void setDefaultValue(@Nullable String defaultValue) {
        DefaultValue = defaultValue;
    }

    public void setDefaultValue(SensitiveValue defaultValue) {
        DefaultValue = defaultValue;
    }
}
