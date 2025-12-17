import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Starting Chat Server on port 5000...");

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.send(message);
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String username;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        void send(String message) {
            out.println(message);
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // First message from client is the username
                username = in.readLine();
                broadcast("[Server] " + username + " joined the chat.", this);

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("/quit")) break;
                    broadcast(username + ": " + message, this);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clients.remove(this);
                broadcast("[Server] " + username + " left the chat.", this);
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }
}
