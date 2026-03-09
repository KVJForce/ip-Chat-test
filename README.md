# ip-Chat-test

En UDP-baserad chattapplikation byggd i Java. Två användare kan skicka meddelanden till varandra över ett nätverk i realtid.

## Filer

| Fil | Beskrivning |
|---|---|
| `Chat.java` | Fullständig chatt – skicka och ta emot meddelanden i samma program |
| `MessageReceiver.java` | Enkel mottagare som lyssnar på en port och visar meddelanden |
| `MessageSender.java` | Enkel sändare som skickar ett enskilt meddelande |

## Kom igång

### Chat.java (rekommenderad)

1. Kompilera:
   ```
   javac Chat.java
   ```
2. Starta:
   ```
   java Chat
   ```
3. Programmet frågar efter:
   - **Ditt namn** – visas framför dina meddelanden
   - **Din port** – valfritt nummer, t.ex. `5000`
   - **Mottagarens IP-adress** – den andra personens IP
   - **Mottagarens port** – porten den andra personen valde

4. Skriv meddelanden direkt i terminalen. Skriv `quit` för att avsluta.

### Exempel: Två personer på samma dator

| | Person A | Person B |
|---|---|---|
| Namn | Alice | Bob |
| Min port | 5000 | 6000 |
| Mottagarens IP | 127.0.0.1 | 127.0.0.1 |
| Mottagarens port | 6000 | 5000 |

### MessageSender och MessageReceiver

Kompilera och kör:
```
javac MessageSender.java
java MessageSender
```
```
javac MessageReceiver.java
java MessageReceiver
```

## Teknologi

- **Protokoll:** UDP (User Datagram Protocol)
- **Språk:** Java
