import java.net.*;
import java.io.*;

public class UDPFileServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(5000);
        byte[] buffer = new byte[4096];

        System.out.println("UDP File Server started on port 5000...");

        // Receive filename
        DatagramPacket filenamePacket = new DatagramPacket(buffer, buffer.length);
        serverSocket.receive(filenamePacket);
        String filename = new String(filenamePacket.getData(), 0, filenamePacket.getLength());
        FileOutputStream fos = new FileOutputStream("received_" + filename);
        System.out.println("Receiving file: " + filename);

        while (true) {
            DatagramPacket filePacket = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(filePacket);
            int length = filePacket.getLength();
            if (length == 0) break; // end signal
            fos.write(filePacket.getData(), 0, length);
            if (length < buffer.length) break; // last packet
        }

        fos.close();
        System.out.println("File received successfully.");
        serverSocket.close();
    }
}
