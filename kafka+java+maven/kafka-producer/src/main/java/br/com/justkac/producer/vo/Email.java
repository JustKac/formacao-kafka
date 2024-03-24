package br.com.justkac.producer.vo;

public class Email {
    
    private final String subject;
    private final String body;
    
    public Email(String userId, String orderId) {
        this.subject = userId;
        this.body = orderId;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
    
}
