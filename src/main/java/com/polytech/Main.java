package com.polytech;

import com.polytech.client.Client;

public class Main {
    private static final String ADDRESS = "localhost";
    private static final int PORT = 61336;

    public static void main(String[] args) {
        if (args.length == 0) {
            Client client = new Client(ADDRESS, PORT);
            client.start();
        } else {

        }
    }
}
