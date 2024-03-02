package edu.esprit.pi.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;
import java.util.Random;

public class ReactiverCompteController {
    // These values should ideally be read from configuration
    private static final String USERNAME = "lilia_jemai";
    private static final String PASSWORD = "lilia@123";
    private static final String API_URL = "https://api.bulksms.com/v1/messages";

    private Random random;
    private static String phoneNumber;

    public ReactiverCompteController(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.random = new Random();
    }

    public void sendVerificationCode() {
        int min = 100000;
        int max = 999999;
        int code = random.nextInt((max - min))+max;

        // The details of the message we want to send
        String message = "{\"to\": \"" +phoneNumber+ "\", \"body\": \"Votre code de réactiver compte: " +code+ "\"}";

        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set up connection
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Set up authentication
            String authString = USERNAME + ":" + PASSWORD;
            String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encodedAuthString);
            connection.setRequestProperty("Content-Type", "application/json");

            // Write data to request
            try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
                out.write(message);
            }

            // Handle response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream response = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(response));
                String replyText;
                while ((replyText = in.readLine()) != null) {
                    System.out.println(replyText);
                }
                in.close();
            } else {
                System.out.println("Failed to send message. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String phoneNumber = "+21627438527"; // Replace this with the desired phone number
        ReactiverCompteController controller = new ReactiverCompteController(phoneNumber);
        controller.sendVerificationCode();
    }

}

