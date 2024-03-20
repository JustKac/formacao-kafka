package br.com.justkac.producer;

import java.util.UUID;

public class NewOrderMain {
    public static void main(String[] args) {

        try (var dispatcher = new KafkaDispatcher()) {
            for (int i = 0; i < 100; i++) {
                var key = UUID.randomUUID().toString(); // Um algoritmo interno do Kafka define para qual partição a
                                                        // mensagem vai a partir da chave.

                var value = key + "67523,1234";
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

                var email = "Thank you for your order! We are processing your order.";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
