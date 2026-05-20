import java.net.Socket;
import java.net.http.HttpClient;

public class RequestProcessor {
    private void process(){
     if (context.getRequest().getUrl().equals("/")){
        context.getResponse().ok("ok");
     }
     else{
        context.getResponse().notFound("C mor");
     }
    }
    private HttpContext context;


   public RequestProcessor(Socket socket){
        context=new HttpContext(socket);
        process();
    }
}
