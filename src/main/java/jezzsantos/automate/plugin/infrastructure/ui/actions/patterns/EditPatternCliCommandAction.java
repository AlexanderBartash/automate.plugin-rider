package jezzsantos.automate.plugin.infrastructure.ui.actions.patterns;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import jezzsantos.automate.plugin.application.IAutomateApplication;
import jezzsantos.automate.plugin.application.interfaces.EditingMode;
import jezzsantos.automate.plugin.common.Action;
import jezzsantos.automate.plugin.common.AutomateBundle;
import jezzsantos.automate.plugin.common.Try;
import jezzsantos.automate.plugin.common.recording.IRecorder;
import jezzsantos.automate.plugin.infrastructure.ui.dialogs.patterns.EditPatternCliCommandDialog;
import jezzsantos.automate.plugin.infrastructure.ui.toolwindows.PatternTreeModel;
import org.jetbrains.annotations.NotNull;

public class EditPatternCliCommandAction extends AnAction {

    private final Action<PatternTreeModel> onSuccess;

    public EditPatternCliCommandAction(Action<PatternTreeModel> onSuccess) {

        super();
        this.onSuccess = onSuccess;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {

        super.update(e);

        var message = AutomateBundle.message("action.EditPatternCliCommand.Title");
        var presentation = e.getPresentation();
        presentation.setDescription(message);
        presentation.setText(message);

        boolean isPatternEditingMode = false;
        var project = e.getProject();
        if (project != null) {
            var application = IAutomateApplication.getInstance(project);
            isPatternEditingMode = application.getEditingMode() == EditingMode.PATTERNS;
        }

        var isAutomationSite = Selection.isCliCommand(e) != null;
        presentation.setEnabledAndVisible(isPatternEditingMode && isAutomationSite);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        IRecorder.getInstance().measureEvent("action.pattern.clicommand.edit", null);

        var project = e.getProject();
        if (project != null) {
            var selected = Selection.isCliCommand(e);
            if (selected != null) {
                var application = IAutomateApplication.getInstance(project);
                var automations = selected.getParent().getAutomation();
                var dialog = new EditPatternCliCommandDialog(project, new EditPatternCliCommandDialog.EditPatternCliCommandDialogContext(selected.getAutomation(), automations));
                if (dialog.showAndGet()) {
                    var context = dialog.getContext();
                    var command = Try.andHandle(project, AutomateBundle.message("action.EditPatternCliCommand.UpdateCommand.Progress.Title"),
                                                () -> application.updatePatternCliCommand(selected.getParent().getEditPath(), context.getId(), context.getName(),
                                                                                          context.getApplicationName(), context.getArguments()),
                                                AutomateBundle.message("action.EditPatternCliCommand.UpdateCommand.Failure.Message"));
                    if (command != null) {
                        this.onSuccess.run(model -> model.updateAutomation(command));
                    }
                }
            }
        }
    }
}
