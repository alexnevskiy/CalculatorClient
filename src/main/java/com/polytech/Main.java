package com.polytech;

import com.polytech.client.Client;
import com.polytech.phrases.Phrases;

public class Main {
    private static final String ADDRESS = "localhost";
    private static final int PORT = 61336;

    public static void main(String[] args) {
        if (args.length == 0) {
            Client client = new Client(ADDRESS, PORT);
            client.start();
        } else {
            if (args.length != 2) {
                System.out.println(Phrases.ARGUMENTS_ERROR.getPhrase());
            } else {
                String address = args[0];
                int port = Integer.parseInt(args[1]);
                Client client = new Client(address, port);
                client.start();
            }
        }
    }
}
