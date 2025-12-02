package chatapp;


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 5555;
    private static final Map<String, DataOutputStream> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Chat server running on port " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ClientHandler(socket)).start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;
        private String username;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                // First message must be username
                String loginMsg = in.readUTF(); // "USERNAME:Ahmed"
                if (loginMsg.startsWith("USERNAME:")) {
                    username = loginMsg.substring(9);
                    clients.put(username, out);
                    System.out.println(username + " connected.");
                }

                // Listen for messages
                String msg;
                while ((msg = in.readUTF()) != null) {
                    if (msg.startsWith("TO:")) {
                        String[] parts = msg.split("\\|", 2);
                        String toUser = parts[0].substring(3);
                        String text = parts[1];

                        if (clients.containsKey(toUser)) {
                            clients.get(toUser).writeUTF(username + ": " + text);
                        } else {
                            out.writeUTF("Server: User " + toUser + " not online.");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(username + " disconnected.");
            } finally {
                if (username != null) {
                    clients.remove(username);
                }
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }
}
