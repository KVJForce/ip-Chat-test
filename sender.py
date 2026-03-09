import socket

def main():
    ip = input("Ange mottagarens IP-adress: ")
    port = int(input("Ange port (t.ex. 5000): "))
    message = input("Skriv meddelandet: ")

    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.sendto(message.encode("utf-8"), (ip, port))
    print(f"Meddelande skickat till {ip}:{port}")
    sock.close()

if __name__ == "__main__":
    main()
