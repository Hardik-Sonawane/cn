import java.net.*;
import java.io.*;

public class clientchat  {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);
        System.out.println("Connected to server: " + socket.getRemoteSocketAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Chat started. Type 'end' to exit.");

        while (true) {
            // Send message to server
            System.out.print("You: ");
            String message = userInput.readLine();
            out.println(message);

            if (message.equalsIgnoreCase("end")) {
                System.out.println("Client exited chat.");
                break;
            }

            // Receive reply from server
            String reply = in.readLine();
            if (reply == null || reply.equalsIgnoreCase("end")) {
                System.out.println("Server exited chat.");
                break;
            }
            System.out.println("Server: " + reply);
        }

        socket.close();
        System.out.println("Connection closed.");
    }
}
