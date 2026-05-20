import java.net.Socket;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpContext {
   private Socket socket;
   private HttpRequest request;
   private HttpResponse response; 

   public HttpContext(Socket s) {
     this.socket= s;
     request= new HttpRequest(socket);
     response = new HttpResponse(socket);    

   }
   public HttpRequest getRequest(){
         return request;
   }
   public HttpResponse getResponse(){
         return response;
   }
   public close(){
      socket.close();
   }
}
