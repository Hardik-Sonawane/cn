import java.net.*;
import java.io.*;

public class UDPFileClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddr = InetAddress.getByName("localhost");
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        byte[] buffer = new byte[4096];

        System.out.print("Enter filename to send: ");
        String filename = userInput.readLine();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found.");
            socket.close();
            return;
        }

        // Send filename first
        DatagramPacket filenamePacket = new DatagramPacket(filename.getBytes(), filename.length(), serverAddr, 5000);
        socket.send(filenamePacket);

        // Send file bytes
        FileInputStream fis = new FileInputStream(file);
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            DatagramPacket filePacket = new DatagramPacket(buffer, bytesRead, serverAddr, 5000);
            socket.send(filePacket);
        }

        // Send empty packet to signal end
        DatagramPacket endPacket = new DatagramPacket(new byte[0], 0, serverAddr, 5000);
        socket.send(endPacket);

        fis.close();
        System.out.println("File sent successfully.");
        socket.close();
    }
}
