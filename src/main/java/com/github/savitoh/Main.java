package com.github.savitoh;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ImdbClient imdbClient = new ImdbClient();
        System.out.println(imdbClient.getTop250Movies());
    }
}