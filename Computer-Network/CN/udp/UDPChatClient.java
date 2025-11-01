import java.net.*;
import java.io.*;

public class UDPChatClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        byte[] buffer = new byte[1024];
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("UDP Chat Client started. Type 'end' to exit.");

        while (true) {
            // Send message
            System.out.print("You: ");
            String message = userInput.readLine();
            DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(), serverAddress, 5000);
            socket.send(sendPacket);
            if (message.equalsIgnoreCase("end")) break;

            // Receive reply
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);
            String reply = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server: " + reply);
            if (reply.equalsIgnoreCase("end")) break;
        }

        socket.close();
    }
}
