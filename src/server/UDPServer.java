package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * UDPServer
 * 
 * A simple UDP server that:
 * - Listens on port 6000 for incoming UDP packets
 * - Processes arithmetic commands (MULTIPLY, ADD, SUB)
 * - Sends response packets back to the client
 * - Connectionless communication
 */
public class UDPServer {

    // Server port number
    private static final int SERVER_PORT = 6000;
    // Maximum packet size
    private static final int MAX_PACKET_SIZE = 1024;

    public static void main(String[] args) {
        System.out.println("UDP Server is running on port " + SERVER_PORT + "...");
        System.out.println("Waiting for client requests...");

        try {
            // Create a DatagramSocket to listen for incoming packets
            DatagramSocket socket = new DatagramSocket(SERVER_PORT);

            // Keep running until the program is stopped
            while (true) {
                // Create a buffer to receive data
                byte[] receiveData = new byte[MAX_PACKET_SIZE];

                // Create a DatagramPacket to receive data
                DatagramPacket receivePacket = new DatagramPacket(
                    receiveData,
                    receiveData.length
                );

                // Wait for a client packet
                socket.receive(receivePacket);

                // Extract client address and port for reply
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Convert received bytes to string command
                String command = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
                ).trim();

                System.out.println("Received from " + clientAddress + ":" + clientPort + 
                                   " -> " + command);

                // Process the command and get response
                String response = processCommand(command);

                // Send response back to client
                byte[] sendData = response.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    clientAddress,
                    clientPort
                );

                socket.send(sendPacket);
                System.out.println("Sent response: " + response);
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Processes a command string and returns the result.
     * Supported commands: MULTIPLY x y, ADD x y, SUB x y
     */
    private static String processCommand(String command) {
        if (command == null || command.isEmpty()) {
            return "ERROR: Empty command.";
        }

        // Split the command into parts
        String[] parts = command.split("\\s+");
        if (parts.length != 3) {
            return "ERROR: Invalid format. Use MULTIPLY, ADD, or SUB with two numbers.";
        }

        String operation = parts[0].toUpperCase();
        String firstStr = parts[1];
        String secondStr = parts[2];

        // Parse the numbers
        int first;
        int second;
        try {
            first = Integer.parseInt(firstStr);
            second = Integer.parseInt(secondStr);
        } catch (NumberFormatException e) {
            return "ERROR: Parameters must be integers.";
        }

        // Perform the operation
        int result;
        switch (operation) {
            case "MULTIPLY":
                result = first * second;
                return "Result: " + result;
            case "ADD":
                result = first + second;
                return "Result: " + result;
            case "SUB":
                result = first - second;
                return "Result: " + result;
            default:
                return "ERROR: Unknown operation. Use MULTIPLY, ADD, or SUB.";
        }
    }
}
