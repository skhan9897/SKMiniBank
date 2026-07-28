package com.bank.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaService {

    private static final String TOPIC = "bank_transactions";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static KafkaProducer<String, String> producer;

    static {
        try {
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            // Adding a small timeout to avoid blocking main thread if Kafka is down
            props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 2000); 

            producer = new KafkaProducer<>(props);
        } catch (Exception e) {
            System.err.println("CRITICAL: Failed to initialize Kafka Producer. Is Kafka running?");
            e.printStackTrace();
        }
    }

    public static void logTransaction(String fromAcc, String toAcc, double amount, String type) {
        if (producer == null) return;

        String message = String.format("Transaction Event: [From: %s, To: %s, Amount: %.2f, Type: %s]", 
                                       fromAcc, toAcc, amount, type);
        
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, fromAcc, message);
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    System.err.println("ERROR: Kafka send failed: " + exception.getMessage());
                } else {
                    System.out.println("DEBUG: Kafka Sent Event to topic " + metadata.topic() + " offset " + metadata.offset());
                }
            });
        } catch (Exception e) {
            System.err.println("ERROR: Error sending message to Kafka: " + e.getMessage());
        }
    }

    public static void close() {
        if (producer != null) {
            producer.close();
        }
    }
}
