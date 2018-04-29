package org.example1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Producer {

	private final KafkaProducer<String, String> kafkaProducer;
	private static final String CLIENT_ID = "SampleProducer";
	private static ExecutorService ex = Executors.newFixedThreadPool(200000);

	public Producer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("client.id", CLIENT_ID);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProducer = new KafkaProducer<>(props);
	}

	public static void main(String args[]) {
		Collection<String> messages = new ArrayList<>();
		IntStream.range(1, 300001).forEach(i -> {
			messages.add("message " + i);
		});

		Producer p = new Producer();
		p.sendMessages(messages).parallelStream().forEach(i -> {
			try {
				i.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});
		ex.shutdownNow();
	}

	public Collection<CompletableFuture<Void>> sendMessages(Collection<String> messages) {
		Collection<CompletableFuture<Void>> sendMessages = new ArrayList<>();
		messages.forEach(m -> {
			sendMessages.add(sendMessage(m));
		});
		return sendMessages;
	}

	public CompletableFuture<Void> sendMessage(String message) {
		return CompletableFuture.runAsync(() -> {
			try {
				System.out.println(message);
				kafkaProducer.send(new ProducerRecord<>("testTopic8", message));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}