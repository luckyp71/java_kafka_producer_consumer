package org.example1;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumer {

	public static void main (String args[]) {
		  Properties properties = new Properties();
	        properties.put("bootstrap.servers", "localhost:9092");
	        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	        properties.put("group.id", "test-group");

	        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
	        List<String> topics = new ArrayList<>();
	        topics.add("testTopic4");
	        kafkaConsumer.subscribe(topics);
	        try{
	            while (true){
	                ConsumerRecords<String, String> records = kafkaConsumer.poll(200);
	                for (ConsumerRecord<String, String> record: records){
	                    System.out.println(String.format("Topic - %s, Partition - %d, Value: %s", record.topic(), record.partition(), record.value()));
	                }
	            }
	        }catch (Exception e){
	            System.out.println(e.getMessage());
	        }finally {
	            kafkaConsumer.close();
	        }
	}
	
}
