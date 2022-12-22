package multicast;

import general.Util;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver {

    public static void main(String[] args) {
        try {
            MulticastSocket socket = new MulticastSocket(Util.MULTICAST_PORT);
            socket.joinGroup(InetAddress.getByName(Util.MULTICAST_IP));

            while (true) {
                DatagramPacket packet = new DatagramPacket(Util.UDP_BUFFER, Util.UDP_BUFFER.length);
                socket.receive(packet);

                String message = new String(Util.UDP_BUFFER, 0, packet.getLength());
                System.out.println("Received message: " + message);
            }
        } catch (IOException ex) {
        }
    }
}
