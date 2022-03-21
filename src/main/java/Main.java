import server.Server;

import java.io.IOException;

public class Main {

    public static final int port = 1800;
    public static void main(String args[]) throws IOException {

        Server server = new Server(port);
        server.startServer();
    }
}
