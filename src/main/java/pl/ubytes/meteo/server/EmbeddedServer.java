package pl.ubytes.meteo.server;

/**
 * Created by bajek on 2/14/15.
 */
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;
public class EmbeddedServer {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8181);
        server.setConnectors(new Connector[] { connector });
        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setContextPath("/");
        ProtectionDomain protectionDomain = EmbeddedServer.class
                .getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        context.setWar(location.toExternalForm());
        server.setHandler(context);
        while (true) {
            try {
                server.start();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            System.in.read();
            server.stop();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }
}