import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private void readRequest(Socket socket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String response = input.readLine();
            while (response != null && response.isEmpty() == false) {
                System.out.println(response);
                response = input.readLine();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void sendReponse(Socket socket) {
        try {
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            output.write("HTTP/1.0 200  \n");
            output.write("Content-Type: text/html\r\n");
            output.write( "\r\n");
            output.write("<html>"); 
            output.write("<h1> Hello world!! <h1>");
            output.write("<h1> Bonjour le monde <h1>");
            output.write("</html>");  
            output.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run(int Number) {
      try {
        ServerSocket serverSocket= new ServerSocket(Number);
        while ( true) {
            Socket clienSocket = serverSocket.accept();
            sendReponse(clienSocket);
            readRequest(clienSocket);
            clienSocket.close();
        }
      } catch (IOException e) {
       
        e.printStackTrace();
      }
    }

}
