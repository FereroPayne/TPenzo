public class HttpContext {
    private HttpRequest request;
    private HttpResponse response;

    public HttpContext(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void printContext() {
        System.out.println("Method: " + request.getMethod());
        System.out.println("URL: " + request.getUrl());
        System.out.println("Protocol: " + request.getProtocol());
    }
}