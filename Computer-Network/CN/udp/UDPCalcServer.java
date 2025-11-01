import java.net.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class UDPCalcServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(5000);
        byte[] buffer = new byte[1024];
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

        System.out.println("UDP Calculator Server started on port 5000...");

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(receivePacket);
            String expr = new String(receivePacket.getData(), 0, receivePacket.getLength());
            InetAddress clientAddr = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            if (expr.equalsIgnoreCase("end")) break;

            String result;
            try {
                Object res = engine.eval(expr);
                result = "Result: " + res;
            } catch (Exception e) {
                result = "Invalid expression";
            }

            DatagramPacket sendPacket = new DatagramPacket(result.getBytes(), result.length(), clientAddr, clientPort);
            serverSocket.send(sendPacket);
        }

        serverSocket.close();
        System.out.println("Server closed.");
    }
}
