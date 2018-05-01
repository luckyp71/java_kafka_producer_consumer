package org.example3;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerCallback implements Callback{

	private final long startTime;
	private final int key;
	private final String message;

	public ProducerCallback(long startTime, int key, String message) {
		this.startTime = startTime;
		this.key = key;
		this.message = message;
	}
	
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		if (metadata != null) {
			System.out.println(
					"message("+key+", "+message+") sent to partition("+0+
					")," + "offset("+metadata.offset()+") in "+ elapsedTime+" ms");
		} else {
			exception.printStackTrace();
		}
	}
}
