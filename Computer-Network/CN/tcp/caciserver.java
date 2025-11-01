import java.io.*;
import java.net.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

public class caciserver {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Server waiting on port 5000...");
        Socket client = server.accept();
        System.out.println("Client connected: " + client.getRemoteSocketAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

        System.out.println("Calculator server ready. Type 'end' to exit.");

        while (true) {
            // Receive expression from client
            String expression = in.readLine();
            if (expression == null || expression.equalsIgnoreCase("end")) {
                System.out.println("Client exited calculator.");
                break;
            }

            try {
                Object result = engine.eval(expression);
                out.println("Result: " + result);
                System.out.println("Client requested: " + expression + " = " + result);
            } catch (Exception e) {
                out.println("Invalid expression.");
                System.out.println("Error evaluating: " + expression);
            }
        }

        client.close();
        server.close();
        System.out.println("Connection closed.");
    }
}
