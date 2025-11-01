import java.io.*;
import java.net.*;

public class FileServe {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Server waiting on port 5000...");

        Socket client = server.accept();
        System.out.println("Client connected: " + client.getRemoteSocketAddress());

        InputStream fileIn = client.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        System.out.println("Ready to receive file.");

        // Read filename first
        String filename = in.readLine();
        File receivedFile = new File("received_" + filename);
        FileOutputStream fileOut = new FileOutputStream(receivedFile);

        byte[] buffer = new byte[4096];
        int bytesRead;

        // Read file bytes
        while ((bytesRead = fileIn.read(buffer)) != -1) {
            fileOut.write(buffer, 0, bytesRead);
            if (bytesRead < 4096) break; // crude end-of-file signal
        }

        fileOut.close();
        System.out.println("File received: " + receivedFile.getName());

        client.close();
        server.close();
        System.out.println("Connection closed.");
    }
}
