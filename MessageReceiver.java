import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class MessageReceiver {
    private static ConcurrentHashMap<String, InetAddress> clients = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Integer> clientPorts = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ange port för servern (t.ex. 5000): ");
        int port = Integer.parseInt(scanner.nextLine());

        DatagramSocket socket = new DatagramSocket(port);
        String localIp = InetAddress.getLocalHost().getHostAddress();
        System.out.println("\nServer startad på " + localIp + ":" + port);
        System.out.println("Väntar på anslutningar...");
        System.out.println("Skriv ett meddelande för att skicka till alla anslutna klienter.");
        System.out.println("Tryck Ctrl+C för att avsluta.\n");

        // Tråd som tar emot meddelanden
        Thread receiver = new Thread(() -> {
            byte[] buffer = new byte[4096];
            try {
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    String message = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                    String senderIp = packet.getAddress().getHostAddress();
                    int senderPort = packet.getPort();
                    String clientKey = senderIp + ":" + senderPort;

                    if (!clients.containsKey(clientKey)) {
                        clients.put(clientKey, packet.getAddress());
                        clientPorts.put(clientKey, senderPort);
                        System.out.println("[" + clientKey + " anslöt]");
                    }

                    System.out.println(clientKey + ": " + message);
                }
            } catch (Exception e) {
                // Socket stängd
            }
        });
        receiver.setDaemon(true);
        receiver.start();

        // Huvudtråden skickar meddelanden till alla klienter
        while (true) {
            String input = scanner.nextLine();
            byte[] data = ("Server: " + input).getBytes("UTF-8");
            for (String key : clients.keySet()) {
                InetAddress addr = clients.get(key);
                int clientPort = clientPorts.get(key);
                DatagramPacket packet = new DatagramPacket(data, data.length, addr, clientPort);
                socket.send(packet);
            }
        }
    }
}
