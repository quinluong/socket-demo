package tcp;

import cache.CacheInterface;
import cache.CacheMap;
import general.CommandEnum;
import general.Util;
import general.ParsedCommand;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServerMultiThread {

    private static class Worker implements Runnable {

        private final Socket socket;
        private final CacheInterface cache;

        public Worker(Socket socket, CacheInterface cache) {
            this.socket = socket;
            this.cache = cache;
        }

        @Override
        public void run() {
            try {
                try {
                    DataInputStream din = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                    while (true) {
                        String command = din.readUTF();
                        System.out.println("Client sent command, client = " + socket + ", command = " + command);

                        ParsedCommand parsedCommand;
                        try {
                            parsedCommand = Util.parseCommand(command);
                        } catch (Exception ex) {
                            System.err.println(ex.getMessage());
                            continue;
                        }

                        if (parsedCommand.getCommandEnum() == CommandEnum.EXIT) {
                            break;
                        } else {
                            switch (parsedCommand.getCommandEnum()) {
                                case SET:
                                    cache.set(parsedCommand.getKey(), parsedCommand.getValue());
                                    dos.writeUTF("OK");
                                    break;

                                case GET:
                                    String value = cache.get(parsedCommand.getKey());
                                    if (value.isEmpty()) {
                                        dos.writeUTF("Key doesn't exist");
                                    } else {
                                        dos.writeUTF(value);
                                    }
                                    break;

                                case DEL:
                                    cache.del(parsedCommand.getKey());
                                    dos.writeUTF("OK");
                                    break;
                            }

                            dos.flush();
                        }
                    }
                } catch (IOException ex) {
                    System.err.println("Connection Error: " + ex);
                }

                socket.close();
                System.out.println("Client closed, " + socket);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            System.out.println("Binding to port " + Util.SERVER_PORT + ", please wait ...");

            serverSocket = new ServerSocket(Util.SERVER_PORT);

            System.out.println("Server started: " + serverSocket);
            System.out.println("Waiting for a client ...");

            CacheInterface cache = new CacheMap();
            ExecutorService executor = Executors.newFixedThreadPool(Util.NUMBER_OF_THREADS);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client accepted: " + socket);

                Worker worker = new Worker(socket, cache);
                executor.execute(worker);
            }
        } catch (IOException ex) {
            System.err.println("Can't start server");
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

}
