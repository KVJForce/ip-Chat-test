import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Chat {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ditt namn: ");
        String name = scanner.nextLine();

        System.out.print("Din port (t.ex. 5000): ");
        int myPort = Integer.parseInt(scanner.nextLine());

        System.out.print("Mottagarens IP-adress: ");
        String remoteIp = scanner.nextLine();

        System.out.print("Mottagarens port: ");
        int remotePort = Integer.parseInt(scanner.nextLine());

        DatagramSocket socket = new DatagramSocket(myPort);
        InetAddress remoteAddress = InetAddress.getByName(remoteIp);

        String localIp = InetAddress.getLocalHost().getHostAddress();
        System.out.println("\nChatt startad! (" + localIp + ":" + myPort + ")");
        System.out.println("Skriv meddelanden nedan. Tryck Esc för att avsluta.\n");

        // Tråd som lyssnar efter inkommande meddelanden
        Thread receiver = new Thread(() -> {
            byte[] buffer = new byte[4096];
            try {
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                    System.out.println(message);
                }
            } catch (Exception e) {
                // Socket stängd, tråden avslutas
            }
        });
        receiver.setDaemon(true);
        receiver.start();

        // Huvudtråden läser tecken och skickar meddelanden
        StringBuilder inputBuffer = new StringBuilder();
        while (true) {
            int b = System.in.read();
            if (b == 27) { // Esc-tangenten
                break;
            } else if (b == '\n' || b == '\r') {
                if (inputBuffer.length() > 0) {
                    String message = name + ": " + inputBuffer.toString();
                    byte[] data = message.getBytes("UTF-8");
                    DatagramPacket packet = new DatagramPacket(data, data.length, remoteAddress, remotePort);
                    socket.send(packet);
                    inputBuffer.setLength(0);
                }
            } else {
                inputBuffer.append((char) b);
            }
        }

        socket.close();
        System.out.println("\nChatten avslutad.");
    }
}
