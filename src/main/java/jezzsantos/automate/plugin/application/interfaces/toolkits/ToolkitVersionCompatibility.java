package jezzsantos.automate.plugin.application.interfaces.toolkits;

import com.google.gson.annotations.SerializedName;
import com.jetbrains.rd.util.UsedImplicitly;
import jezzsantos.automate.core.AutomateConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

public class ToolkitVersionCompatibility {

    @SerializedName(value = "Compatibility")
    private AutomateConstants.ToolkitRuntimeVersionCompatibility toolkitCompatibility;
    @SerializedName(value = "Toolkit")
    private ToolkitVersions toolkit;
    @SerializedName(value = "Runtime")
    private ToolkitVersions runtime;

    @UsedImplicitly
    public ToolkitVersionCompatibility() {}

    @TestOnly
    public ToolkitVersionCompatibility(@NotNull String toolkitVersion, @NotNull String runtimeVersion, AutomateConstants.ToolkitRuntimeVersionCompatibility compatibility) {

        this.toolkitCompatibility = compatibility;
        this.toolkit = new ToolkitVersions(toolkitVersion);
        this.runtime = new ToolkitVersions(runtimeVersion);
    }

    public ToolkitVersions getToolkitVersion() {return this.toolkit;}

    public ToolkitVersions getRuntimeVersion() {return this.runtime;}

    public AutomateConstants.ToolkitRuntimeVersionCompatibility getToolkitCompatibility() {return this.toolkitCompatibility;}

    public boolean isToolkitIncompatible() {

        return this.toolkitCompatibility != AutomateConstants.ToolkitRuntimeVersionCompatibility.COMPATIBLE;
    }

    public static class ToolkitVersions {

        @SerializedName(value = "Created")
        private String created;
        @SerializedName(value = "Installed")
        private String installed;

        public ToolkitVersions(@NotNull String toolkitVersion) {

            this.created = toolkitVersion;
            this.installed = toolkitVersion;
        }

        public String getCreated() {return this.created;}

        public String getInstalled() {return this.installed;}
    }
}

