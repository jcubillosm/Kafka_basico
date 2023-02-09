package com.dev.kafka.producers;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DevProducer {
	public static final Logger log = LoggerFactory.getLogger(DevProducer.class);

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		//props.put("compression.type", "gzip");
		//props.put("linger.ms", "0");		// Define cada cuanto se envía el grupo de mensajes. Default = 0
		//props.put("batch.size", "32384");   // Grupos de mensajes que se envían juntos para no enviar mensaje por mensaje. Los batch se guardan en el bufferMemory
		//props.put("transactional.id", "devKafka-producer");
		//props.put("buffer.memory", "33554432");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		//Asíncrono
		try(Producer<String, String> producer = new KafkaProducer<>(props);){
			for(int i = 0; i< 1000; i++) {
				
				
				producer.send(new ProducerRecord<String, String>("dev-topic", String.valueOf(i), String.valueOf(i) +" mensaje ")); 
			}
			producer.flush();
		}
		log.info(" Processing time = {} ms ", (System.currentTimeMillis() - startTime));
		
		// ------------------------------------------------
		// Síncrono
		/*try(Producer<String, String> producer = new KafkaProducer<>(props);){
			for(int i = 0; i< 1000; i++) {
				// El uso de get() lo hace síncrono
				producer.send(new ProducerRecord<String, String>("dev-topic", String.valueOf(i), String.valueOf(i) +" envio")).get(); 
			}
			producer.flush();
		}catch (InterruptedException | ExecutionException e) {
			log.error(" Message producer ", e);
		}*/
		
		
	}
		
}
