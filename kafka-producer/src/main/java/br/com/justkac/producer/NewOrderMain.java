package br.com.justkac.producer;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.justkac.producer.vo.Email;
import br.com.justkac.producer.vo.Order;

public class NewOrderMain {
    public static void main(String[] args) {

        try (var orderDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<Email>()) {
                for (int i = 0; i < 100; i++) {

                    var userId = UUID.randomUUID().toString(); // Um algoritmo interno do Kafka define para qual partição a mensagem vai a partir da chave.
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);

                    Order order = new Order(userId, orderId, amount);
                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);

                    var subject = "Subject";
                    var body = "Thank you for your order! We are processing your order.";
                    Email email = new Email(subject, body);
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
