import java.io.*;
import java.net.*;

public class FileClien {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);
        System.out.println("Connected to server: " + socket.getRemoteSocketAddress());

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        OutputStream fileOutStream = socket.getOutputStream();

        System.out.print("Enter filename to send: ");
        String filename = userInput.readLine();
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("File not found: " + filename);
            socket.close();
            return;
        }

        // Send filename first
        out.println(file.getName());

        FileInputStream fileIn = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead;

        System.out.println("Sending file: " + file.getName());
        while ((bytesRead = fileIn.read(buffer)) != -1) {
            fileOutStream.write(buffer, 0, bytesRead);
        }

        fileIn.close();
        System.out.println("File sent successfully.");

        socket.close();
        System.out.println("Connection closed.");
    }
}
