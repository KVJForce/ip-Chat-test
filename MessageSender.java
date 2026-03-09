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

        System.out.print("Skriv meddelandet: ");
        String message = scanner.nextLine();

        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(ip);
        byte[] data = message.getBytes("UTF-8");
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        socket.send(packet);

        System.out.println("Meddelande skickat till " + ip + ":" + port);
        socket.close();
        scanner.close();
    }
}
