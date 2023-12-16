Custom Prometheus metrics: Servlet and Spring Boot
=

This is an example of how to create and expose custom Prometheus metrics for a Servlet and a Spring Boot application.

Setup
-

Checkout the code and run

```
mvn -f servlet/pom.xml clean install
```

and

```
mvn -f spring-boot/pom.xml clean install spring-boot:repackage
```

Run
-

```
docker-compose up
```

Open `http://localhost:9090` in your Browser: You see the Prometheus UI. In the "Expression" field input `uptime_count`
and switch to the "Graph" view: You see the uptime of both the Servlet and the Spring Boot application.
If you use `numberOfCallsToMetrics_total` you see the number of calls that Prometheus makes to scrape the metrics from
the Servlet application as the Servlet that exposes the metrics counts all requests at the same time.
If you use `numberOfCallsToHello_total` you see the number of calls to the `/api/hello` endpoint of the Spring Boot
application. Open `http://localhost:8080/api/hello` in your Browser and refresh the page a few times and you should see
that number increasing in Prometheus.