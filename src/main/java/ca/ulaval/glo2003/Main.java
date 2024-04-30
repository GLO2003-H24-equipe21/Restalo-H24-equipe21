package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.HealthResource;
import ca.ulaval.glo2003.api.exceptions.*;
import io.sentry.Sentry;
import java.net.URI;
import java.util.Objects;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
    public static String BASE_URI = "http://0.0.0.0:8080/";

    public static HttpServer startServer() {
        String port = System.getenv("PORT");
        if (!Objects.isNull(port)) {
            BASE_URI = BASE_URI.replace("8080/", port + "/");
        }

        ApplicationContext applicationContext = new ApplicationContext();

        final ResourceConfig resourceConfig =
                new ResourceConfig()
                        .register(new HealthResource())
                        .register(applicationContext.getRestaurantResource())
                        .register(applicationContext.getReservationResource())
                        .register(applicationContext.getReviewResource())
                        .register(applicationContext.getSearchResource())
                        .register(new ProductionExceptionMapper())
                        .register(new ConstraintViolationExceptionMapper())
                        .register(new NullPointerExceptionMapper())
                        .register(new IllegalArgumentExceptionMapper())
                        .register(new NotFoundExceptionMapper());

        String sentryDsn = System.getenv("SENTRY_DSN");
        if (!Objects.isNull(sentryDsn)) {
            Sentry.init(
                    options -> {
                        options.setDsn(System.getenv("SENTRY_DSN"));
                        options.setEnableMetrics(true);
                        options.setTracesSampleRate(1.0);
                        options.setDebug(true);
                    });
            Sentry.metrics().increment("start", 1);
        }

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
    }

    public static void main(String[] args) {
        startServer();
        System.out.printf("Jersey app started with endpoints available at %s%n", BASE_URI);
    }
}
