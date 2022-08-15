package jezzsantos.automate.plugin.infrastructure.ui.actions;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.ex.ComboBoxAction;
import jezzsantos.automate.plugin.application.IAutomateApplication;
import jezzsantos.automate.plugin.application.interfaces.EditingMode;
import jezzsantos.automate.plugin.application.interfaces.PatternDefinition;
import jezzsantos.automate.plugin.infrastructure.AutomateBundle;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PatternsListToolbarAction extends ComboBoxAction {

    @SuppressWarnings("DialogTitleCapitalization")
    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);

        String message = AutomateBundle.message("action.PatternsListToolbarAction.NoSelected.Title");
        boolean isAuthoringMode = false;
        boolean isPatternEditingMode = false;
        var project = e.getProject();
        if (project != null) {
            var application = IAutomateApplication.getInstance(project);
            isAuthoringMode = application.isAuthoringMode();
            isPatternEditingMode = application.getEditingMode() == EditingMode.Patterns;
            var currentPattern = application.getCurrentPattern();
            if (currentPattern != null) {
                message = currentPattern.getName();
            }
        }

        var presentation = e.getPresentation();
        presentation.setDescription(AutomateBundle.message("action.PatternsListToolbarAction.Title"));
        presentation.setText(message);
        presentation.setEnabledAndVisible(isAuthoringMode && isPatternEditingMode);
    }

    @Override
    protected @NotNull DefaultActionGroup createPopupActionGroup(JComponent component) {
        final var actions = new DefaultActionGroup();

        var project = DataManager.getInstance().getDataContext(component).getData(CommonDataKeys.PROJECT);
        if (project != null) {
            var application = IAutomateApplication.getInstance(project);
            var patterns = application.getPatterns();
            var isAnyPatterns = !patterns.isEmpty();
            if (isAnyPatterns) {
                var isNoCurrentPattern = patterns.stream()
                        .noneMatch(PatternDefinition::getIsCurrent);
                if (isNoCurrentPattern) {
                    actions.add(new PatternListItemAction());
                }
                for (var pattern : patterns) {
                    actions.add(new PatternListItemAction(pattern.getName(), pattern.getId()));
                }
            }
        }

        return actions;
    }
}