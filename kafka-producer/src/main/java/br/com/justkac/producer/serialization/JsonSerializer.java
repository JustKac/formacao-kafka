package br.com.justkac.producer.serialization;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer<T> implements Serializer<T> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            return mapper.writeValueAsString(data).getBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    
}
