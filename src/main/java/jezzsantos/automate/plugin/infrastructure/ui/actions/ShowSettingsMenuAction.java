package jezzsantos.automate.plugin.infrastructure.ui.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import jezzsantos.automate.plugin.common.AutomateBundle;
import jezzsantos.automate.plugin.common.recording.IRecorder;
import jezzsantos.automate.plugin.infrastructure.settings.ApplicationSettingsConfigurable;
import org.jetbrains.annotations.NotNull;

public class ShowSettingsMenuAction extends AnAction {

    public ShowSettingsMenuAction() {

        super();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {

        super.update(e);

        var message = AutomateBundle.message("action.ShowSettings.Title");
        var presentation = e.getPresentation();
        presentation.setText(message);
        presentation.setIcon(AllIcons.General.GearPlain);
        presentation.setEnabledAndVisible(true);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        IRecorder.getInstance().measureEvent("action.display.settings.show", null);

        var project = e.getProject();
        if (project != null) {
            ShowSettingsUtil.getInstance().showSettingsDialog(project, ApplicationSettingsConfigurable.class);
        }
    }
}
