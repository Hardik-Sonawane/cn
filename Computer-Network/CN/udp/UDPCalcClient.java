import java.net.*;
import java.io.*;

public class UDPCalcClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddr = InetAddress.getByName("localhost");
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        byte[] buffer = new byte[1024];

        System.out.println("UDP Calculator Client. Type 'end' to exit.");

        while (true) {
            System.out.print("Enter expression: ");
            String expr = userInput.readLine();
            DatagramPacket sendPacket = new DatagramPacket(expr.getBytes(), expr.length(), serverAddr, 5000);
            socket.send(sendPacket);
            if (expr.equalsIgnoreCase("end")) break;

            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);
            String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server: " + result);
        }

        socket.close();
    }
}
