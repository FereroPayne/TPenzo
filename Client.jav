import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ProtocolServer {
    private int port;
    private List<String> receivedData;
    
    public ProtocolServer(int port) {
        this.port = port;
        this.receivedData = new ArrayList<>();
    }
    
    /**
     * Starts the server and listens for client connections
     * Protocol: HELLO <- OK -> DATA value <- OK
     */
    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("[SERVER] Listening on port " + port);
            
            while (true) {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("[SERVER] Client connected from " + clientSocket.getInetAddress());
                
                // Handle client in a separate thread
                new Thread(new ClientHandler(clientSocket, receivedData)).start();
            }
            
        } catch (Exception e) {
            System.out.println("[SERVER] Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Inner class to handle each client connection
     */
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private List<String> receivedData;
        
        public ClientHandler(Socket socket, List<String> receivedData) {
            this.socket = socket;
            this.receivedData = receivedData;
        }
        
        @Override
        public void run() {
            try {
                // Create input and output streams
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                
                // Step 1: Receive HELLO
                String message = input.readLine();
                System.out.println("[SERVER] Received: " + message);
                
                if (message != null && message.equals("HELLO")) {
                    // Step 2: Send OK
                    System.out.println("[SERVER] Sending: OK");
                    output.println("OK");
                    
                    // Step 3: Receive DATA
                    message = input.readLine();
                    System.out.println("[SERVER] Received: " + message);
                    
                    if (message != null && message.startsWith("DATA")) {
                        // Extract and validate the data
                        String[] parts = message.split(" ", 2);
                        if (parts.length == 2) {
                            String sensorValue = parts[1];
                            
                            // Validate format (should contain a number and unit)
                            if (isValidSensorData(sensorValue)) {
                                // Store the received data
                                receivedData.add(sensorValue);
                                System.out.println("[SERVER] Valid data stored: " + sensorValue);
                                
                                // Step 4: Send OK confirmation
                                System.out.println("[SERVER] Sending: OK");
                                output.println("OK");
                            } else {
                                System.out.println("[SERVER] Invalid data format: " + sensorValue);
                                output.println("OK"); // Still send OK per protocol
                            }
                        }
                    } else {
                        System.out.println("[SERVER] Error: Expected DATA message");
                    }
                } else {
                    System.out.println("[SERVER] Error: Expected HELLO message");
                }
                
                // Close connection
                socket.close();
                System.out.println("[SERVER] Client disconnected");
                
            } catch (Exception e) {
                System.out.println("[SERVER] Handler error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        /**
         * Validates sensor data format (e.g., "10.5m", "12.3m")
         */
        private boolean isValidSensorData(String data) {
            return data != null && data.matches("\\d+(\\.\\d+)?[a-zA-Z]+");
        }
    }
    
    public List<String> getReceivedData() {
        return receivedData;
    }
    
    public static void main(String[] args) {
        ProtocolServer server = new ProtocolServer(8000);
        server.start();
    }
}