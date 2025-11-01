import java.io.*;
import java.net.*;

public class CalculatorClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);
        System.out.println("Connected to server: " + socket.getRemoteSocketAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Simple Calculator. Type 'end' to exit.");

        while (true) {
            System.out.print("Enter expression: ");
            String expression = userInput.readLine();
            out.println(expression);

            if (expression.equalsIgnoreCase("end")) {
                System.out.println("Exiting calculator.");
                break;
            }

            // Receive result from server
            String result = in.readLine();
            System.out.println("Server: " + result);
        }

        socket.close();
        System.out.println("Connection closed.");
    }
}
