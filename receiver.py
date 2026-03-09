import socket

def main():
    port = int(input("Ange port att lyssna på (t.ex. 5000): "))

    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.bind(("0.0.0.0", port))
    print(f"Lyssnar på port {port}... (Ctrl+C för att avsluta)")

    try:
        while True:
            data, addr = sock.recvfrom(4096)
            print(f"Meddelande från {addr[0]}:{addr[1]}: {data.decode('utf-8')}")
    except KeyboardInterrupt:
        print("\nAvslutar.")
    finally:
        sock.close()

if __name__ == "__main__":
    main()
