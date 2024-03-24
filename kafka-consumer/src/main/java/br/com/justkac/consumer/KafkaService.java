package br.com.justkac.consumer;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import br.com.justkac.consumer.serialization.JsonDeserializer;

public class KafkaService<T> implements Closeable {

    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction<T> parse;

    KafkaService(String groupId, String topic, ConsumerFunction<T> parse) {
        this.consumer = new KafkaConsumer<String, T>(properties(groupId));
        this.parse = parse;
        consumer.subscribe(Collections.singletonList(topic));

    }

    KafkaService(String groupId, Pattern topic, ConsumerFunction<T> parse) {
        this.consumer = new KafkaConsumer<String, T>(properties(groupId));
        this.parse = parse;
        consumer.subscribe(topic);

    }

    void run() {
        while (true) {
            var records = consumer.poll(Duration.ofMillis(1000));   // O commit do poll só é efetuado ao final de processamento de todas as mensagens.
            if (records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");
                for (var record : records) {
                    parse.consume(record);
                }
            }
        }
    }

    private static Properties properties(String groupId) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());  // Personalizando o ID do consumidor
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");    // Limita o carregamento de mensagens a 1 por poll.
        return properties;
    }

    @Override
    public void close() throws IOException {
        consumer.close();
    }

}
