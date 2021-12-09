package com.polytech.client;

import com.polytech.phrases.Phrases;
import com.polytech.protocol.CalculatorPackage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Set;

public class ReceiverThread extends Thread {
    private final Socket socket;
    private final DataInputStream inputStream;
    private final Set<Byte> identifications;

    public ReceiverThread(Socket socket, Set<Byte> identifications) throws IOException {
        this.socket = socket;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.identifications = identifications;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                if (hasMessage()) {
                    CalculatorPackage message = readMessage();
                    checkMessage(message);
                }
            } catch (IOException exception) {
                closeReceiverThread();
            }
        }
    }

    private boolean hasMessage() throws IOException {
        return inputStream.available() > 0;
    }

    private CalculatorPackage readMessage() throws IOException {
        byte[] messageBytes = inputStream.readNBytes(20);
        return new CalculatorPackage(messageBytes);
    }

    private void checkMessage(CalculatorPackage message) {
        switch (message.getError()) {
            case 0:
                boolean idCheck = checkId(message.getId());
                if (idCheck) {
                    printMessage(message);
                }
                break;
            case 1:
                checkId(message.getId());
                printErrorMessage(Phrases.OPERATION_ERROR, message);
                break;
            case 2:
                checkId(message.getId());
                printErrorMessage(Phrases.ZERO_DIVISION_ERROR, message);
                break;
            case 3:
                checkId(message.getId());
                printErrorMessage(Phrases.INVALID_ARG_ERROR, message);
                break;
            case 4:
                checkId(message.getId());
                printErrorMessage(Phrases.IDENTIFIER_REPEAT_ERROR, message);
                break;
            case 5:
                checkId(message.getId());
                printErrorMessage(Phrases.OUT_OF_BOUNDS_ERROR, message);
                break;
            case 6:
                checkId(message.getId());
                printErrorMessage(Phrases.TIME_OUT_ERROR, message);
                break;
            case 7:
                checkId(message.getId());
                printErrorMessage(Phrases.GROVE_STREET_FAMILIES_ERROR, message);
                printResultInConsole(message.getFirstArgument());
                break;
        }
    }

    private boolean checkId(byte id) {
        if (identifications.contains(id)) {
            identifications.remove(id);
            return true;
        } else {
            return false;
        }
    }

    private void printMessage(CalculatorPackage message) {
        printReceivedMessage(message);
        printResultInConsole(message.getFirstArgument());
    }

    private void printReceivedMessage(CalculatorPackage message) {
        System.out.println(Phrases.RECEIVED_MESSAGE.getPhrase() + message.getId());
    }

    private void printResultInConsole(double result) {
        System.out.println("Результат операции равен " + result);
    }

    private void printErrorMessage(Phrases phrase, CalculatorPackage message) {
        printReceivedMessage(message);
        System.out.println(phrase.getPhrase());
    }

    public void closeReceiverThread() {
        try {
            inputStream.close();
            socket.close();
            interrupt();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
