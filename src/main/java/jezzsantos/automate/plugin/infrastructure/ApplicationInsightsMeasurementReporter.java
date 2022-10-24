package jezzsantos.automate.plugin.infrastructure;

import com.intellij.openapi.Disposable;
import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import jezzsantos.automate.plugin.common.IMeasurementReporter;
import jezzsantos.automate.plugin.infrastructure.ui.ApplicationInsightsClient;
import org.apache.commons.lang.time.StopWatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class ApplicationInsightsMeasurementReporter implements IMeasurementReporter, Disposable {

    private static final String RequestName = "jbrd-plugin-use";
    private final TelemetryClient client;
    private final StopWatch stopwatch;
    private boolean reportingEnabled;
    private RequestTelemetry request;

    public ApplicationInsightsMeasurementReporter() {

        this.stopwatch = new StopWatch();
        this.client = ApplicationInsightsClient.getClient();
        this.reportingEnabled = false;
    }

    @Override
    public void dispose() {

        if (this.client != null) {
            this.client.flush();
        }
    }

    @Override
    public void enableReporting(@NotNull String machineId, @NotNull String sessionId) {

        this.reportingEnabled = true;
        var context = this.client.getContext();
        context.getCloud().setRoleInstance(machineId);
        context.getDevice().setId(machineId);
        context.getUser().setId(machineId);
        context.getSession().setId(sessionId);
    }

    @Override
    public void measureStartSession(@NotNull String messageTemplate, @Nullable Object... args) {

        this.stopwatch.start();
        this.request = new RequestTelemetry();
        this.request.setName(RequestName);
        this.request.setId(createRequestId());
    }

    @Override
    public void measureEndSession(boolean success, @NotNull String messageTemplate, @Nullable Object... args) {

        if (this.reportingEnabled) {
            if (this.request != null) {
                this.stopwatch.stop();
                this.request.setSuccess(success);
                this.request.setDuration(new Duration(this.stopwatch.getTime()));
                this.client.trackRequest(this.request);
            }
        }

        this.client.flush();
    }

    @Override
    public void measureEvent(@NotNull String eventName, @Nullable Map<String, String> context) {

        if (this.reportingEnabled) {
            this.client.trackEvent(eventName.toLowerCase(), context, Map.of());
        }
    }

    @NotNull
    private String createRequestId() {

        return UUID.randomUUID().toString().replace("-", "");
    }
}
