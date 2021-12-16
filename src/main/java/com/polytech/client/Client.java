package com.polytech.client;

import com.polytech.phrases.Phrases;
import com.polytech.protocol.CalculatorPackage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Client extends Thread {
    private final String address;
    private final int port;
    private Socket socket;
    private DataOutputStream outputStream;
    private ReceiverThread receiverThread;
    private final Scanner scanner;
    private final Set<Byte> identifications;
    private final Random random = new Random();
    private CalculatorPackage message;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        this.identifications = Collections.synchronizedSet(new HashSet<>());
    }

    @Override
    public void run() {
        try {
            try {
                socket = new Socket(address, port);
            } catch (IOException exception) {
                System.out.println(Phrases.SERVER_ERROR.getPhrase());
                return;
            }
            outputStream = new DataOutputStream(socket.getOutputStream());
            receiverThread = new ReceiverThread(socket, identifications);
            receiverThread.start();

            while (!socket.isClosed()) {
                message = new CalculatorPackage();
                byte id = readMessageFromConsole();
                writeMessage(id);
            }
        } catch (IOException exception) {
            closeClient();
        }
    }

    private byte readMessageFromConsole() {
        readOperation();
        readArguments();
        return generateId();
    }

    private void readOperation() {
        System.out.println(Phrases.ENTER_OPERATION.getPhrase());
        boolean correctOperation = false;
        while (!correctOperation) {
            if (scanner.hasNext()) {
                correctOperation = checkOperation(scanner.nextLine().trim());
            }
            if (!correctOperation) {
                System.out.println(Phrases.BAD_OPERATION.getPhrase());
            }
        }
    }

    private boolean checkOperation(String operation) {
        boolean exit = checkExit(operation);
        if (exit) {
            closeClient();
            return true;
        }
        switch (operation) {
            case "+":
                message.setSpeed((byte) 1);
                message.setOperation((byte) 0);
                return true;
            case "-":
                message.setSpeed((byte) 1);
                message.setOperation((byte) 1);
                return true;
            case "*":
                message.setSpeed((byte) 1);
                message.setOperation((byte) 2);
                return true;
            case "/":
                message.setSpeed((byte) 1);
                message.setOperation((byte) 3);
                return true;
            case "sqrt":
                message.setSpeed((byte) 0);
                message.setOperation((byte) 0);
                return true;
            case "fact":
                message.setSpeed((byte) 0);
                message.setOperation((byte) 1);
                return true;
            default:
                return false;
        }
    }

    private void readArguments() {
        if (message.getSpeed() == 1) {
            System.out.println(Phrases.ENTER_TWO_ARGUMENTS.getPhrase());
            boolean correctArguments = false;
            while (!correctArguments) {
                if (scanner.hasNext()) {
                    correctArguments = checkArguments(scanner.nextLine().trim().split("\\s"));
                }
                if (!correctArguments) {
                    System.out.println(Phrases.BAD_TWO_ARGUMENTS.getPhrase());
                }
            }
        } else {
            System.out.println(Phrases.ENTER_OPERATION_TIME.getPhrase());
            boolean correctTime = false;
            while (!correctTime) {
                if (scanner.hasNext()) {
                    correctTime = checkTime(scanner.nextLine().trim());
                }
                if (!correctTime) {
                    System.out.println(Phrases.BAD_OPERATION_TIME.getPhrase());
                }
            }

            System.out.println(Phrases.ENTER_ARGUMENT.getPhrase());
            boolean correctArgument = false;
            while (!correctArgument) {
                if (scanner.hasNext()) {
                    correctArgument = checkArgument(scanner.nextLine().trim());
                }
                if (!correctArgument) {
                    System.out.println(Phrases.BAD_ARGUMENT.getPhrase());
                }
            }
        }
    }

    private byte generateId() {
        boolean correctId = false;
        byte[] id = new byte[1];
        while (!correctId) {
            random.nextBytes(id);
            if (identifications.contains(id[0])) {
                continue;
            }
            message.setId(id[0]);
            identifications.add(id[0]);
            correctId = true;
        }
        return id[0];
    }

    private boolean checkArguments(String[] arguments) {
        boolean exit = checkExit(arguments[0]);
        if (exit) {
            closeClient();
            return true;
        }
        if (arguments.length != 2) {
            return false;
        }
        try {
            double argumentFirst = Double.parseDouble(arguments[0]);
            double argumentSecond = Double.parseDouble(arguments[1]);
            message.setFirstArgument(argumentFirst);
            message.setSecondArgument(argumentSecond);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    private boolean checkTime(String timeString) {
        boolean exit = checkExit(timeString);
        if (exit) {
            closeClient();
            return true;
        }
        try {
            int time = Integer.parseInt(timeString);
            if ((time < 1) || (time > 65535)) {
                return false;
            }
            message.setTime((short) time);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    private boolean checkArgument(String argumentString) {
        boolean exit = checkExit(argumentString);
        if (exit) {
            closeClient();
            return true;
        }
        try {
            double argument = Double.parseDouble(argumentString);
            message.setFirstArgument(argument);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    private boolean checkExit(String argument) {
        return argument.equals("-exit");
    }

    private void writeMessage(byte id) throws IOException {
        outputStream.write(message.toByte());
        System.out.println(Phrases.SEND_MESSAGE.getPhrase() + id + "\n");
    }

    private void closeClient() {
        scanner.close();
        try {
            outputStream.close();
            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        receiverThread.interrupt();
        try {
            receiverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Клиент выключен.");
        System.exit(0);
    }
}
