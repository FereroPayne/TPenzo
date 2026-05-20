import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private int port;

    public WebServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("[SERVER] Listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[SERVER] Client connected");

                // Handle client
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Parse request
                HttpRequest request = new HttpRequest(socket);
                
                // Create response
                HttpResponse response = new HttpResponse(socket);
                
                // Create context
                HttpContext context = new HttpContext(request, response);
                context.printContext();

                // Send response
                String body = "<h1>Welcome to Web Server</h1>" +
                              "<p>Method: " + request.getMethod() + "</p>" +
                              "<p>URL: " + request.getUrl() + "</p>";
                response.send(body);

                socket.close();

            } catch (Exception e) {
                System.out.println("Handler error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        WebServer server = new WebServer(8000);
        server.start();
    }
}