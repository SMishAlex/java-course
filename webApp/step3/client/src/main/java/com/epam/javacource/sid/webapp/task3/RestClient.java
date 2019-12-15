package com.epam.javacource.sid.webapp.task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RestClient {

    public static void main(String[] args) {

        String address = "http://localhost:8080?param=1,13,42";
        System.out.println("totalCallsNumber\t|clientNumber\t|time per single request (ms)\t");
        System.out.println(":---:|:---:|:---:");
        for (int totalCallsNumber = 1_000; totalCallsNumber <= 16_000; totalCallsNumber *= 2) {
            for (int clientNumber = 100; clientNumber <= 800; clientNumber *= 2) {
                long difference = loadTest(address, clientNumber, totalCallsNumber);
                System.out.printf("%s\t|%s\t|%s\n",
                        totalCallsNumber, clientNumber, ((double) difference) / totalCallsNumber);
            }
        }
    }

    private static long loadTest(String address, int clientNumbers, int totalCallsNumber) {
        ExecutorService executorService = Executors.newFixedThreadPool(clientNumbers);
        long millisBefore = System.currentTimeMillis();
        for (int i = 0; i < totalCallsNumber; i++) {
            executorService.submit(() -> callGetMethod(address));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            //waiting for all joins
        }
        long millisAfter = System.currentTimeMillis();
        return millisAfter - millisBefore;
    }

    private static void callGetMethod(String address) {
        StringBuilder strBuf = new StringBuilder();
        HttpURLConnection conn = null;
        int failedCalls = 0;
        try {
            URL url = new URL(address);
            boolean processed = false;
            // retrying while response is not 200 =)
            do {
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    processed = conn.getResponseCode() == 200;

                } catch (Exception ignored) {
                    failedCalls ++;
                }
            } while (!processed);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String output;
                while ((output = reader.readLine()) != null) {
                    strBuf.append(output);
                    strBuf.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        if (failedCalls != 0) {
            System.err.println(failedCalls);
        }
    }
}
