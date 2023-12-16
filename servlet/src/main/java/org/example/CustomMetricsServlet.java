package org.example;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Summary;
import io.prometheus.client.exporter.MetricsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CustomMetricsServlet extends MetricsServlet {

    private static final long START_TIME = System.currentTimeMillis();

    private static final Summary UPTIME = Summary.build("uptime", "Uptime of the application")
            .create();

    private static final Counter COUNTER = Counter.build("numberOfCallsToMetrics", "Number of calls to the CustomMetricsServlet")
            .create();

    public CustomMetricsServlet() {
        final var collectorRegistry = CollectorRegistry.defaultRegistry;
        collectorRegistry.register(UPTIME);
        collectorRegistry.register(COUNTER);

        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> UPTIME.observe(System.currentTimeMillis() - START_TIME), 0, 5, TimeUnit.SECONDS);
    }

    @Override
    protected void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws IOException {
        COUNTER.inc();
        super.doGet(httpServletRequest, httpServletResponse);
    }
}