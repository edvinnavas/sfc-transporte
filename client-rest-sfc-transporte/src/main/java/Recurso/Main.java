package Recurso;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;

public class Main implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BASE_URI = "http://0.0.0.0:8018/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("Recurso");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with endpoints available at "
                + "%s%nHit Ctrl-C to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }

}
