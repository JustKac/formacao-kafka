package br.com.justkac.consumer.serialization;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, new TypeReference<T>() {});
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
