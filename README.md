# Java and Kafka Integration

This repository is a simple example on how to simulate the communication between kafka's producer and consumer in java.

Prerequisites:
1. JDK 1.8
2. Apache Kafka
3. Zookeeper
4. Maven
5. Eclipse IDE (or you could switch to your fav one)

The example is divided into three packages, each package contains kafka's producer and consumer classes.
The purpose of dividing the example is to implement the three different ways of kafka's producer in sending the messsage.
In example1 package, you might see the kafka's producer uses java 8's CompletableFuture in sending the message.
Example2, the kafka's producer sends the message synchronously and for the example3, the kafka's producer sends the message 
asynchronously, in which its producer class is extending thread class as well as implementing the kafka's callback interface. 
	
Following are the steps to simulate the communication between kafka's producer and consumer of each package:
1. Start ZooKeeper Server
2. Start Kafka Server (Broker)
3. Create Kafka Topic
3. Run Producer class
4. Run Consumer class
