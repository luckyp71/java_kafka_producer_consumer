package org.example2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.stream.IntStream;

public class Producer {

	private final KafkaProducer<String, String> producer;

	private static final String KAFKA_SERVER_URL = "localhost";
	private static final int KAFKA_SERVER_PORT = 9092;
	private static final String CLIENT_ID = "SampleProducer";

	public Producer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
		props.put("client_id", CLIENT_ID);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(props);
	}

	public static void main(String args[]) {
		Producer p = new Producer();
		try {
			IntStream.range(1, 300001).forEach(i -> {
				System.out.println("message " + i);
				p.producer.send(new ProducerRecord<>("testTopic10", String.valueOf(i), "message " + i));
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			p.producer.close();
		}
	}
}
