package com.bamzhy13.superblog.blogbackend.tools;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

public class StephenProducer {

    public static void main(String[] args) throws Exception {
        new StephenProducer().sendExpenses("Hui Zhang", "zhangh9@oregonstate.edu","1000", "200","80");
    }

    private static final String QUEUE_NAME = "hui-mq";

    public void sendExpenses(String username, String email, String rent, String food, String game) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername("hui");
        factory.setPassword("HuiOSU");

        // Allows client to establish a connection over TLS
        factory.useSslProtocol();

        //Replace the URL with your information
        factory.setHost("b-e01a602c-5662-4ffe-a635-733e9ba114fe.mq.us-west-2.amazonaws.com");
        factory.setPort(5671);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            JsonObject messageJS = new JsonObject();
            messageJS.addProperty("username", username);
            messageJS.addProperty("email", email);

            JsonObject expenses = new JsonObject();
            expenses.addProperty("rent", rent);
            expenses.addProperty("food", food);
            expenses.addProperty("game", game);

            messageJS.add("expenses", expenses);

            channel.basicPublish("", QUEUE_NAME, null, messageJS.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + messageJS.toString() + "'");

        }

    }
}
