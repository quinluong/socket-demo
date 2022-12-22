package udp;

import general.Util;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {

    public static void main(String[] args) {
        DatagramSocket datagramSocket = null;

        try {
            System.out.println("Binding to port " + Util.SERVER_PORT + ", please wait ...");

            datagramSocket = new DatagramSocket(Util.SERVER_PORT);

            System.out.println("Server started: " + Util.datagramSocketToString(datagramSocket));
            System.out.println("Waiting for messages ...");

            while (true) {
                DatagramPacket inputPacket = new DatagramPacket(Util.UDP_BUFFER, Util.UDP_BUFFER.length);
                datagramSocket.receive(inputPacket);

                String input = new String(inputPacket.getData(), 0, inputPacket.getLength());
                System.out.println(String.format("Received message from client, client = %s, message = %s", inputPacket.getSocketAddress(), input));

                byte[] outputBytes = "OK".getBytes();
                DatagramPacket outputPacket = new DatagramPacket(
                        outputBytes,
                        outputBytes.length,
                        inputPacket.getAddress(),
                        inputPacket.getPort()
                );
                datagramSocket.send(outputPacket);
            }
        } catch (IOException ex) {
            System.err.println("Can't start server");
        } finally {
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
    }
}
