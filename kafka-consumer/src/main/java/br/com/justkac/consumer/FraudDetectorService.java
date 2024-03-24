package br.com.justkac.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.justkac.consumer.vo.Order;

public class FraudDetectorService {
    public static void main(String[] args) {
        var fraudDetectorService = new FraudDetectorService();
        try (var service = new KafkaService<Order>(FraudDetectorService.class.getSimpleName(), "ECOMMERCE_NEW_ORDER",
                fraudDetectorService::parse);) {
            service.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("---------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // ignoring
            e.printStackTrace();
        }
        System.out.println("Order processed");
    }

}
