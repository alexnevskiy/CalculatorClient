package com.polytech.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.Socket;

public class ReceiverThread extends Thread {
    private final Socket socket;
    private final DataInputStream inputStream;

    public ReceiverThread(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                if (hasMessage()) {

                }
            } catch (IOException exception) {

            }
        }
    }

    private boolean hasMessage() throws IOException {
        return inputStream.available() > 0;
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
