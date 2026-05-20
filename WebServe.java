import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public void run(int NumP){
        ServerSocket serverSocket = new ServerSocket(NumP);
        while (true) {
            Socket clienSocket= serverSocket.accept();
            process = new RequestProcessor(clienSocket);
            clienSocket.close();
        }
    }
}
