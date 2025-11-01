import java.net.*;
import java.io.*;

public class serverchat
 {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Server waiting on port 5000...");

        Socket client = server.accept();
        System.out.println("Client connected: " + client.getRemoteSocketAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Chat started. Type 'end' to exit.");

        while (true) {
            // Read message from client
            String clientMsg = in.readLine();
            if (clientMsg == null || clientMsg.equalsIgnoreCase("end")) {
                System.out.println("Client exited chat.");
                break;
            }
            System.out.println("Client: " + clientMsg);

            // Send reply to client
            System.out.print("You: ");
            String reply = serverInput.readLine();
            out.println(reply);

            if (reply.equalsIgnoreCase("end")) {
                System.out.println("Server exited chat.");
                break;
            }
        }

        client.close();
        server.close();
        System.out.println("Connection closed.");
    }
}
