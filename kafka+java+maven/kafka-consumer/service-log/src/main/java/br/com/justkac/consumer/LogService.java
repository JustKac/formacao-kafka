package br.com.justkac.consumer;

import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class LogService {
    public static void main(String[] args) {
        var logService = new LogService();
        try (var service = new KafkaService<String>(LogService.class.getSimpleName(), Pattern.compile("ECOMMERCE.*"),
                logService::parse)) {
            service.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("---------------------");
        System.out.println("LOG: " + record.topic());
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
    }

}
