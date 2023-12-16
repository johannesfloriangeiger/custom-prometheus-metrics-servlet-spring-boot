package org.example;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Summary;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class UptimeMetricInitialisation implements ApplicationRunner {

    private static final long START_TIME = System.currentTimeMillis();

    private static final Summary UPTIME = Summary.build("uptime", "Uptime of the application")
            .create();

    private final CollectorRegistry collectorRegistry;

    public UptimeMetricInitialisation(final CollectorRegistry collectorRegistry) {
        this.collectorRegistry = collectorRegistry;
    }

    @Override
    public void run(final ApplicationArguments args) {
        UPTIME.register(this.collectorRegistry);
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> UPTIME.observe(System.currentTimeMillis() - START_TIME), 0, 5, TimeUnit.SECONDS);
    }
}
