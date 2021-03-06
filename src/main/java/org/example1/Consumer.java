package org.example1;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

public class Consumer {

	public static void main(String args[]) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("group.id", "SampleConsumer");

		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
		List<String> topics = new ArrayList<>();
		topics.add("testTopic1");
		kafkaConsumer.subscribe(topics);
		try {
			while (true) {
				ConsumerRecords<String, String> records = kafkaConsumer.poll(200);
				for (ConsumerRecord<String, String> record : records) {
					System.out.println(String.format("Topic - %s, Partition - %d, Value: %s", record.topic(),
							record.partition(), record.value()));
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			kafkaConsumer.close();
		}
	}
}