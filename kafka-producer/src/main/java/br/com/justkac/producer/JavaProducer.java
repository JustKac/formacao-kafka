package br.com.justkac.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class JavaProducer {
    public static void main(String[] args) {
        var producer = new KafkaProducer<String, String>(properties());
        var value = "132123,67523,1234";
        var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", value, value);
        try {
            producer.send(record, (data, ex) -> {
                if (ex != null) {
                    ex.printStackTrace();
                    return;
                }
                System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset "
                        + data.offset() + "/ timestamp " + data.timestamp());
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.close();
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

}
