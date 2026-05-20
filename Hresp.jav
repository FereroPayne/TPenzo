import java.io.PrintWriter;
import java.net.Socket;

public class HttpResponse {
    private Socket socket;
    private int statusCode;
    private String statusMessage;

    public HttpResponse(Socket socket) {
        this.socket = socket;
        this.statusCode = 200;
        this.statusMessage = "OK";
    }

    public void send(String body) {
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Send status line
            output.println("HTTP/1.1 " + statusCode + " " + statusMessage);

            // Send headers
            output.println("Content-Type: text/html");
            output.println("Content-Length: " + body.length());
            output.println("");

            // Send body
            output.println(body);
            output.flush();

        } catch (Exception e) {
            System.out.println("Error sending response: " + e.getMessage());
        }
    }

    public void setStatus(int code, String message) {
        this.statusCode = code;
        this.statusMessage = message;
    }
}