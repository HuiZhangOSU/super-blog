package com.bamzhy13.superblog.blogbackend.tools;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.*;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;

public class MQConsumer {
    private final static String QUEUE_NAME = "hui-mq";

    public static void main(String[] args) throws Exception {
        new MQConsumer().startConsumer();
    }

    public void startConsumer() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername("hui");
        factory.setPassword("HuiOSU");

        //Replace the URL with your information
        factory.setHost("b-e01a602c-5662-4ffe-a635-733e9ba114fe.mq.us-west-2.amazonaws.com");
        factory.setPort(5671);

        // Allows client to establish a connection over TLS
        factory.useSslProtocol();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            sendEmailByMessage(delivery);
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

    void sendEmailByMessage(Delivery delivery) {

        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        JsonObject jo = JsonParser.parseString(message).getAsJsonObject();

        String username = jo.get("username").getAsString();
        String email = jo.get("email").getAsString();

        JsonObject expenses = jo.get("expenses").getAsJsonObject();

        StringBuilder sb = new StringBuilder();
        sb.append("Your rent expense is: ").append(expenses.get("rent").getAsString());
        sb.append("; ");

        sb.append("Your food expense is: ").append(expenses.get("food").getAsString());
        sb.append("; ");

        sb.append("Your game expense is: ").append(expenses.get("game").getAsString());
        sb.append("; ");

        try {
            MailSender.sendMail(username + "'s expense info", email, sb.toString());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        System.out.println(" [x] Received '" + message + "'");
    }

}
