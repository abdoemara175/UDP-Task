package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * UDPClient
 * 
 * A simple UDP client that:
 * - Connects to a UDP server on localhost:6000
 * - Reads arithmetic commands from user input
 * - Sends requests as UDP packets
 * - Receives and displays responses
 */
public class UDPClient {

    // Server address and port
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 6000;
    // Maximum packet size
    private static final int MAX_PACKET_SIZE = 1024;

    public static void main(String[] args) {
        System.out.println("UDP Client");
        System.out.println("Connected to server on " + SERVER_HOST + ":" + SERVER_PORT);
        System.out.println("Type commands like: MULTIPLY 4 6 or ADD 10 5");
        System.out.println("Type EXIT to quit.");
        System.out.println();

        try {
            // Create a DatagramSocket for sending/receiving
            DatagramSocket socket = new DatagramSocket();

            // Get server address
            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);

            // Read commands from console
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8)
            );

            String command;
            while (true) {
                System.out.print("Client> ");
                command = reader.readLine();

                if (command == null || command.isEmpty()) {
                    continue;
                }

                // Exit if user types EXIT
                if (command.equalsIgnoreCase("EXIT")) {
                    System.out.println("Closing client...");
                    break;
                }

                // Send command to server as UDP packet
                byte[] sendData = command.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    serverAddress,
                    SERVER_PORT
                );

                socket.send(sendPacket);
                System.out.println("Sent: " + command);

                // Receive response from server
                byte[] receiveData = new byte[MAX_PACKET_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(
                    receiveData,
                    receiveData.length
                );

                socket.receive(receivePacket);

                // Convert response to string
                String response = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
                );

                System.out.println("Server> " + response);
            }

            socket.close();
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
