package org.example3;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import java.util.Properties;
import java.util.stream.IntStream;

public class Producer extends Thread {

	private final KafkaProducer<Integer, String> producer;
	private final String topic;
	private static int messageNo = 1;
	
	private static final String KAFKA_SERVER_URL = "localhost";
	private static final int KAFKA_SERVER_PORT = 9092;
	private static final String CLIENT_ID = "SampleProducer";

	public Producer(String topic) {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(props);
		this.topic = topic;
	}
	
	@Override
	public void run() {
		IntStream.range(0, 300001).forEach(i ->{
				String messageStr = "message " + messageNo +i;
				long startTime = System.currentTimeMillis();
				producer.send(new ProducerRecord<>(topic, messageNo+i, messageStr),
						new ProducerCallback(startTime, messageNo+i, messageStr));
		});
	}
	
	public static void main (String args[]) {
		Producer p = new Producer("testTopic1");
		p.start();
	}
}