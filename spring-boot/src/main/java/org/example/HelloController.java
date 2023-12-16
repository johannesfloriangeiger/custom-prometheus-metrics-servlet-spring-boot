package org.example;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final static Counter COUNTER = Counter.build("numberOfCallsToHello", "Number of calls to the HelloController")
            .create();

    public HelloController(final CollectorRegistry collectorRegistry) {
        COUNTER.register(collectorRegistry);
    }

    @GetMapping("api/hello")
    public ResponseEntity<String> read() {
        COUNTER.inc();

        return ResponseEntity.ok("Hello");
    }
}
