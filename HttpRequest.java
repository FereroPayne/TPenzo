import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpRequest {
    private String method;
    private String url;

    private void readClientRequest(Socket socket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String res = input.readLine();
            String [] firstLine= res.split(" ");
            if (firstLine.length >= 2){
                method = firstLine[0];
                url = firstLine[1];
            } 
            String response = input.readLine();
            while (response != null && response.isEmpty() == false) {
                System.out.println(response);
                response = input.readLine();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public HttpRequest(Socket socket) {
      readClientRequest(socket);
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

}
