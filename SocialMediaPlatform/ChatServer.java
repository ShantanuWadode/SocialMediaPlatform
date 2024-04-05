import java.io.*;
import java.net.*;
import java.util.*;
import javax.sound.sampled.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static final Map<String, Socket> clients = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final BufferedReader in;
        private final PrintWriter out;
        private String username;

        public ClientHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {
                username = in.readLine();
                System.out.println(username + " connected");
                clients.put(username, clientSocket);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(username + ": " + inputLine);
                    sendToAll(username + ": " + inputLine);

                    // Notify with sound
                    playNotificationSound();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (username != null) {
                    clients.remove(username);
                    System.out.println(username + " disconnected");
                    sendToAll(username + " disconnected");
                }
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendToAll(String message) {
            for (Socket socket : clients.values()) {
                try {
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void playNotificationSound() {
            try {
                File soundFile = new File("notification.wav"); // Replace with your sound file path
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
