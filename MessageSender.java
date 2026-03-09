import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class MessageSender {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ange mottagarens IP-adress: ");
        String ip = scanner.nextLine();

        System.out.print("Ange port (t.ex. 5000): ");
        int port = Integer.parseInt(scanner.nextLine());

        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(ip);

        System.out.println("Ansluten till " + ip + ":" + port);
        System.out.println("Skriv meddelanden nedan. Tryck Esc + Enter för att avsluta.\n");

        // Tråd som tar emot svar från servern
        Thread receiver = new Thread(() -> {
            byte[] buffer = new byte[4096];
            try {
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                    System.out.println(msg);
                }
            } catch (Exception e) {
                // Socket stängd
            }
        });
        receiver.setDaemon(true);
        receiver.start();

        while (true) {
            String message = scanner.nextLine();
            if (message.length() == 1 && message.charAt(0) == 27) {
                break;
            }
            byte[] data = message.getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
        }

        socket.close();
        scanner.close();
        System.out.println("Avslutar.");
    }
}
