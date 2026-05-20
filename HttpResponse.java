
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.classfile.BufWriter;
import java.net.Socket;

public class HttpResponse {
    private BufferedWriter output;

    public HttpResponse(Socket socket) {
        try {
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

   public void ok(String message){
          output.write("HTTP/1.0 200 ok \n");
            
            output.flush();
   }

   public void notFound(String message){
     output.write("HTTP/1.0 404 C foutu\n");
        output.flush();
   }
}
