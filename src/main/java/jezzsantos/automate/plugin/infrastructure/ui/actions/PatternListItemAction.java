package jezzsantos.automate.plugin.infrastructure.ui.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import jezzsantos.automate.plugin.application.IAutomateApplication;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PatternListItemAction extends AnAction {

    @NotNull
    private final String name;
    @Nullable
    private final String id;

    public PatternListItemAction() {
        this.name = "";
        this.id = null;
    }

    public PatternListItemAction(@NotNull String name, @NotNull String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        var message = this.name;
        var presentation = e.getPresentation();
        presentation.setDescription(message);
        presentation.setText(message);
        var project = e.getProject();
        if (project != null) {
            var application = IAutomateApplication.getInstance(project);
            var currentPattern = application.getCurrentPattern();
            var isCurrentPattern = currentPattern != null && currentPattern.getId().equals(this.id);
            presentation.setIcon(isCurrentPattern ? AllIcons.Actions.Checked : null);
            presentation.setEnabledAndVisible(true);
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (this.id != null) {
            var project = e.getProject();
            if (project != null) {
                var application = IAutomateApplication.getInstance(project);
                try {
                    application.setCurrentPattern(this.id);
                } catch (Exception ex) {
                    throw new RuntimeException("Failed to set current pattern", ex);
                }
            }
        }
    }
}