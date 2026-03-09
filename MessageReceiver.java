import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MessageReceiver {
    public static void main(String[] args) throws Exception {
        int port = 5000;

        DatagramSocket socket = new DatagramSocket(port);
        String localIp = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Lyssnar på " + localIp + ":" + port);
        System.out.println("Tryck Ctrl+C för att avsluta.\n");

        byte[] buffer = new byte[4096];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
            String senderIp = packet.getAddress().getHostAddress();
            int senderPort = packet.getPort();

            System.out.println("Meddelande från " + senderIp + ":" + senderPort + ": " + message);
        }
    }
}
