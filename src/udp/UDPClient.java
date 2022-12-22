package udp;

import general.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            System.out.println("Client started: " + Util.datagramSocketToString(datagramSocket));

            while (true) {
                System.out.println("Enter your message:");

                String output = (new BufferedReader(new InputStreamReader(System.in))).readLine();

                byte[] inputBytes = output.getBytes();
                DatagramPacket outputPacket = new DatagramPacket(
                        inputBytes,
                        inputBytes.length,
                        InetAddress.getByName(Util.SERVER_IP),
                        Util.SERVER_PORT
                );
                datagramSocket.send(outputPacket);

                DatagramPacket inputPacket = new DatagramPacket(Util.UDP_BUFFER, Util.UDP_BUFFER.length);
                datagramSocket.receive(inputPacket);

                String input = new String(inputPacket.getData(), 0, inputPacket.getLength());
                System.out.println(String.format("Received message from server, server = %s, message = %s", inputPacket.getSocketAddress(), input));
            }
        } catch (IOException ex) {
            System.err.println("Can't connect to server");
        } finally {
        }
    }
}
