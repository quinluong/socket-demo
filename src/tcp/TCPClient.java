package tcp;

import general.CommandEnum;
import general.Util;
import general.ParsedCommand;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        Scanner sc = null;

        try {
            socket = new Socket(Util.SERVER_IP, Util.SERVER_PORT); // Connect to server
            System.out.println("Connected: " + socket);

            DataInputStream din = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            sc = new Scanner(System.in);

            while (true) {
                ParsedCommand parsedCommand;
                try {
                    parsedCommand = Util.parseCommand(sc.nextLine());
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                    continue;
                }

                String input = parsedCommand.toString();
                System.out.println("input = " + input);

                dos.writeUTF(input);
                dos.flush();

                if (parsedCommand.getCommandEnum() == CommandEnum.EXIT) {
                    break;
                } else {
                    String output = din.readUTF();
                    System.out.println("output = " + output);
                }
            }
        } catch (IOException ex) {
            System.err.println("Can't connect to server");
        } finally {
            if (sc != null) {
                sc.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
    }
}
