package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.HealthResource;
import ca.ulaval.glo2003.api.exceptions.ConstraintViolationExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.IllegalArgumentExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.NullPointerExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.RuntimeExceptionMapper;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:8080/";

    public static HttpServer startServer() {
        ApplicationContext applicationContext = new ApplicationContext();

        final ResourceConfig rc =
                new ResourceConfig()
                        .register(new HealthResource())
                        .register(applicationContext.getRestaurantResource())
                        .register(applicationContext.getReservationResource())
                        .register(applicationContext.getSearchResource())
                        .register(new ConstraintViolationExceptionMapper())
                        .register(new NullPointerExceptionMapper())
                        .register(new IllegalArgumentExceptionMapper())
                        .register(new RuntimeExceptionMapper());

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) {
        startServer();
        System.out.printf("Jersey app started with endpoints available at %s%n", BASE_URI);
    }
}
