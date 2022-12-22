package multicast;

import general.Util;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastSender {

    public static void main(String[] args) throws InterruptedException {
        try {
            DatagramSocket socket = new DatagramSocket();

            long counter = 0;

            while (true) {
                String message = "This is message from sender, counter = " + counter++;
                byte[] messageBytes = message.getBytes();

                DatagramPacket packet = new DatagramPacket(
                        messageBytes,
                        messageBytes.length,
                        InetAddress.getByName(Util.MULTICAST_IP),
                        Util.MULTICAST_PORT
                );
                socket.send(packet);

                System.out.println("Sender sent packet with message: " + message);

                Thread.sleep(1000);
            }
        } catch (IOException ex) {
        } finally {
        }
    }
}
