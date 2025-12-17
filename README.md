# UDP-Task – Java UDP Client–Server Calculator

This project demonstrates a **connectionless** UDP-based client–server application written in pure Java using `DatagramSocket`.
The server listens on a fixed port and processes arithmetic calculation requests sent by a UDP client.

Supported operations:
- `MULTIPLY x y` → returns `Result: x * y`
- `ADD x y` → returns `Result: x + y`
- `SUB x y` → returns `Result: x - y`

If the client sends an invalid command or wrong format, the server returns an error message.

> **Note**: This project uses **UDP (User Datagram Protocol)** for connectionless communication.

---

## UDP vs TCP: Key Differences

| Feature | UDP | TCP |
|---------|-----|-----|
| **Connection** | Connectionless | Connection-oriented |
| **Reliability** | Unreliable (packets may be lost) | Reliable (guaranteed delivery) |
| **Ordering** | No guaranteed order | In-order delivery |
| **Speed** | Faster (less overhead) | Slower (more overhead) |
| **Use Case** | Real-time apps, streaming, gaming | File transfer, email, HTTPS |
| **Port** | Any available port | Any available port |

### Why UDP for this project?
- **Simplicity**: No need to establish a connection
- **Speed**: Less overhead, faster responses
- **Stateless**: Each packet is independent
- **Learning**: Great for understanding network protocols

---

## Project Structure

```
UDP-Task/
│── README.md
└── src/
    ├── server/
    │   └── UDPServer.java
    └── client/
        └── UDPClient.java
```

---

## Requirements

- Java Development Kit (JDK) 8 or later
- Understanding of UDP/IP networking concepts

---

## How to Compile

From the `UDP-Task` root directory:

```bash
cd src
javac server/UDPServer.java client/UDPClient.java
```

This generates `.class` files in the same directories.

---

## How to Run the Server

From inside the `src` directory:

```bash
java server.UDPServer
```

The server will:
- Start listening on port `6000`
- Wait for incoming UDP packets
- Process each request and send a response
- Continue running until you stop it manually (Ctrl + C)

Expected output:
```
UDP Server is running on port 6000...
Waiting for client requests...
```

---

## How to Run the Client

In a **separate** terminal window, from the same `src` directory:

```bash
java client.UDPClient
```

The client will:
- Connect to server on `localhost:6000`
- Allow you to type calculation commands
- Send requests and display responses
- Type `EXIT` to close

Expected initial output:
```
UDP Client
Connected to server on localhost:6000
Type commands like: MULTIPLY 4 6 or ADD 10 5
Type EXIT to quit.
```

---

## Example Usage

### Example 1 – Multiplication

**Client input:**
```
MULTIPLY 4 6
```

**Server response:**
```
Result: 24
```

### Example 2 – Addition

**Client input:**
```
ADD 10 5
```

**Server response:**
```
Result: 15
```

### Example 3 – Subtraction

**Client input:**
```
SUB 20 8
```

**Server response:**
```
Result: 12
```

### Example 4 – Invalid Command

**Client input:**
```
DIVIDE 10 2
```

**Server response:**
```
ERROR: Unknown operation. Use MULTIPLY, ADD, or SUB.
```

### Example 5 – Invalid Parameters

**Client input:**
```
ADD five ten
```

**Server response:**
```
ERROR: Parameters must be integers.
```

---

## Technical Details

### UDPServer.java
- Uses `DatagramSocket` to listen on port 6000
- Receives `DatagramPacket` objects from clients
- Parses command strings from packet data
- Sends response packets back to client address/port
- Handles multiple requests continuously

### UDPClient.java
- Uses `DatagramSocket` to communicate with server
- Reads user input from console
- Creates `DatagramPacket` with command and sends to server
- Receives response packet from server
- Displays response to user

### Communication Protocol
```
Client                              Server
  |
  |--- DatagramPacket "MULTIPLY 4 6" --->
  |                                    (processes)
  |<--- DatagramPacket "Result: 24" ----|
  |
```

---

## Notes

- Make sure the server is running **before** starting the client
- UDP packets might occasionally be lost in real networks (not in localhost)
- Server port 6000 can be changed in `UDPServer.java` and `UDPClient.java`
- Each client request is independent (no session maintained)
- This project is suitable for beginners learning network programming

---

## Author

Created as a university practical assignment for learning UDP networking in Java.
