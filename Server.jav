import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProtocolClient {
    private String serverAddress;
    private int serverPort;
    
    public ProtocolClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }
    
    /**
     * Sends sensor data to the server following the custom protocol
     * Protocol: HELLO -> OK -> DATA value -> OK
     */
    public void sendSensorData(String sensorValue) {
        try {
            // Connect to server
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server at " + serverAddress + ":" + serverPort);
            
            // Create input and output streams
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Step 1: Send HELLO
            System.out.println("[CLIENT] Sending: HELLO");
            output.println("HELLO");
            
            // Step 2: Receive OK
            String response = input.readLine();
            System.out.println("[CLIENT] Received: " + response);
            
            if (response != null && response.equals("OK")) {
                // Step 3: Send DATA with sensor value
                String dataMessage = "DATA " + sensorValue;
                System.out.println("[CLIENT] Sending: " + dataMessage);
                output.println(dataMessage);
                
                // Step 4: Receive OK confirmation
                response = input.readLine();
                System.out.println("[CLIENT] Received: " + response);
                
                if (response != null && response.equals("OK")) {
                    System.out.println("[CLIENT] Data transmission successful!");
                } else {
                    System.out.println("[CLIENT] Error: Invalid server response");
                }
            } else {
                System.out.println("[CLIENT] Error: Server did not respond with OK to HELLO");
            }
            
            // Close connection
            socket.close();
            System.out.println("[CLIENT] Connection closed");
            
        } catch (Exception e) {
            System.out.println("[CLIENT] Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ProtocolClient client = new ProtocolClient("localhost", 8000);
        
        // Send multiple sensor readings
        client.sendSensorData("10.5m");
        try {
            Thread.sleep(1000); // Wait between messages
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        client.sendSensorData("12.3m");
    }
}
