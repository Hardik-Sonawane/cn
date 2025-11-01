import java.net.*;
import java.io.*;

public class UDPChatServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(5000);
        byte[] buffer = new byte[1024];

        System.out.println("UDP Chat Server started on port 5000...");

        InetAddress clientAddress = null;
        int clientPort = -1;
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            // Receive message
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(receivePacket);
            clientAddress = receivePacket.getAddress();
            clientPort = receivePacket.getPort();

            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            if (message.equalsIgnoreCase("end")) {
                System.out.println("Client exited chat.");
                break;
            }
            System.out.println("Client: " + message);

            // Send reply
            System.out.print("You: ");
            String reply = userInput.readLine();
            DatagramPacket sendPacket = new DatagramPacket(reply.getBytes(), reply.length(), clientAddress, clientPort);
            serverSocket.send(sendPacket);

            if (reply.equalsIgnoreCase("end")) {
                System.out.println("Server exited chat.");
                break;
            }
        }

        serverSocket.close();
    }
}
