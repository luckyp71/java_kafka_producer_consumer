package org.example2;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Properties;

public class Consumer {
	
	private final KafkaConsumer<String, String> consumer;
	private static final String KAFKA_SERVER_URL = "localhost";
	private static final int KAFKA_SERVER_PORT = 9092;
	private static final String CLIENT_ID = "SampleClient";
	
	public Consumer() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_URL+":"+KAFKA_SERVER_PORT);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, CLIENT_ID);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(props);
	}
	
	public static void main (String args[]) {
		Consumer c = new Consumer();
		
		Collection<String> topics = new ArrayList<>();
		topics.add("testTopic1");
		c.consumer.subscribe(topics);
		
		try {
			while(true) {
				ConsumerRecords<String, String> records = c.consumer.poll(10);
				for(ConsumerRecord<String, String> record: records) {
					System.out.println(String.format("Topic: - %s, Partition: - %d, Value: %s",
							record.topic(), record.partition(), record.value()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.consumer.close();
		}
	}
}