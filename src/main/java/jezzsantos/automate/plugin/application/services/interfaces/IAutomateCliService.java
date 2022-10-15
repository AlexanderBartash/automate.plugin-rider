package jezzsantos.automate.plugin.application.services.interfaces;

import com.intellij.openapi.application.ApplicationManager;
import jezzsantos.automate.core.AutomateConstants;
import jezzsantos.automate.plugin.application.interfaces.AllStateLite;
import jezzsantos.automate.plugin.application.interfaces.CliLogEntry;
import jezzsantos.automate.plugin.application.interfaces.drafts.*;
import jezzsantos.automate.plugin.application.interfaces.patterns.Attribute;
import jezzsantos.automate.plugin.application.interfaces.patterns.PatternDetailed;
import jezzsantos.automate.plugin.application.interfaces.patterns.PatternLite;
import jezzsantos.automate.plugin.application.interfaces.toolkits.ToolkitDetailed;
import jezzsantos.automate.plugin.application.interfaces.toolkits.ToolkitLite;
import jezzsantos.automate.plugin.common.StringWithDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

public interface IAutomateCliService {

    static IAutomateCliService getInstance() {

        return ApplicationManager.getApplication().getService(IAutomateCliService.class);
    }

    @NotNull
    String getExecutableName();

    @NotNull
    String getDefaultExecutableLocation();

    @NotNull
    CliExecutableStatus tryGetExecutableStatus(@NotNull String currentDirectory, @NotNull StringWithDefault executablePath);

    boolean isCliInstalled(@NotNull String currentDirectory);

    void refreshCliExecutableStatus();

    @NotNull
    List<CliLogEntry> getCliLog();

    void addPropertyChangedListener(@NotNull PropertyChangeListener listener);

    void removePropertyChangedListener(@NotNull PropertyChangeListener listener);

    @NotNull
    AllStateLite listAllAutomation(@NotNull String currentDirectory, boolean forceRefresh);

    @NotNull
    List<PatternLite> listPatterns(@NotNull String currentDirectory);

    @NotNull
    List<ToolkitLite> listToolkits(@NotNull String currentDirectory);

    @NotNull
    List<DraftLite> listDrafts(@NotNull String currentDirectory);

    @Nullable
    ToolkitLite findToolkitById(@NotNull String currentDirectory, @NotNull String id);

    @NotNull
    PatternLite createPattern(@NotNull String currentDirectory, @NotNull String name) throws Exception;

    @Nullable
    PatternLite getCurrentPatternInfo(@NotNull String currentDirectory);

    @NotNull
    PatternDetailed getCurrentPatternDetailed(@NotNull String currentDirectory) throws Exception;

    void setCurrentPattern(@NotNull String currentDirectory, @NotNull String id) throws Exception;

    @Nullable
    String publishCurrentPattern(@NotNull String currentDirectory, boolean installLocally, @Nullable String version) throws Exception;

    @NotNull
    Attribute addPatternAttribute(@NotNull String currentDirectory, @NotNull String parentEditPath, @NotNull String id, boolean isRequired, @NotNull AutomateConstants.AttributeDataType type, @Nullable String defaultValue, @Nullable List<String> choices) throws Exception;

    @NotNull
    Attribute updatePatternAttribute(@NotNull String currentDirectory, @NotNull String parentEditPath, @NotNull String id, @Nullable String name, boolean isRequired, @NotNull AutomateConstants.AttributeDataType type, @Nullable String defaultValue, @Nullable List<String> choices) throws Exception;

    void deletePatternAttribute(@NotNull String currentDirectory, @NotNull String editPath, @NotNull String name) throws Exception;

    void installToolkit(@NotNull String currentDirectory, @NotNull String location) throws Exception;

    @NotNull
    ToolkitDetailed getCurrentToolkitDetailed(@NotNull String currentDirectory) throws Exception;

    @NotNull
    DraftLite createDraft(@NotNull String currentDirectory, @NotNull String toolkitName, @NotNull String name) throws Exception;

    @Nullable
    DraftLite getCurrentDraftInfo(@NotNull String currentDirectory);

    @NotNull
    DraftDetailed getCurrentDraftDetailed(@NotNull String currentDirectory) throws Exception;

    void setCurrentDraft(@NotNull String currentDirectory, @NotNull String id) throws Exception;

    @NotNull
    DraftElement addDraftElement(@NotNull String currentDirectory, @NotNull String parentConfigurePath, boolean isCollection, @NotNull String elementName, @NotNull Map<String, String> nameValuePairs) throws Exception;

    @NotNull
    DraftElement updateDraftElement(@NotNull String currentDirectory, @NotNull String configurationPath, @NotNull Map<String, String> nameValuePairs) throws Exception;

    void deleteDraftElement(@NotNull String currentDirectory, @NotNull String expression) throws Exception;

    @NotNull
    DraftUpgradeReport upgradeCurrentDraft(@NotNull String currentDirectory, boolean force) throws Exception;

    void deleteCurrentDraft(String currentDirectory) throws Exception;

    @NotNull
    LaunchPointExecutionResult executeLaunchPoint(@NotNull String currentDirectory, @NotNull String configurationPath, @NotNull String launchPointName) throws Exception;
}

