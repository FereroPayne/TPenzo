import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpRequest {
    private String method;
    private String url;
    private String protocol;

    public HttpRequest(Socket socket) {
        readClientRequest(socket);
    }

    private void readClientRequest(Socket socket) {
        try {
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            // Read first line: "GET /index.html HTTP/1.1"
            String firstLine = input.readLine();
            if (firstLine != null) {
                String[] parts = firstLine.split(" ");
                if (parts.length >= 3) {
                    this.method = parts[0];      // GET, POST, etc.
                    this.url = parts[1];         // /index.html
                    this.protocol = parts[2];    // HTTP/1.1
                }
            }

            // Read headers
            String line;
            while ((line = input.readLine()) != null && !line.isEmpty()) {
                System.out.println(line);
            }

        } catch (Exception e) {
            System.out.println("Error reading request: " + e.getMessage());
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }
}