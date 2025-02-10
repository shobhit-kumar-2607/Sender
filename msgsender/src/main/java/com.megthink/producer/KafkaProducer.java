package com.megthink;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import utils.ReadConfigFile;

@Service
public class KafkaProducer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

//	private final String IN_QUEUE = ReadConfigFile.getProperties().getProperty("spring.kafka.in.queue");
	 private final String CoreQueue =ReadConfigFile.getProperties().getProperty("spring.kafka.core.queue");
	// ReadConfigFile.getProperties().getProperty("spring.kafka.out.queue");

	public Boolean sendMessage(String message, String sessionId) {
		Boolean flag = false;
		try {
			logger.debug(
					"[sessionId={}] KafkaProducer.sendMessage(): Trying to send message to queue with timestamp: [{}]",
					sessionId, new Timestamp(System.currentTimeMillis()));
			kafkaTemplate.send(CoreQueue, message);
			logger.debug(
					"[sessionId={}] KafkaProducer.sendMessage(): Successfully sent message to queue with timestamp: [{}]",
					sessionId, new Timestamp(System.currentTimeMillis()));
			flag = true;
		} catch (Exception e) {
			logger.error(
					"[sessionId={}] KafkaProducer.sendMessage(): Exception occurred while sending message into queue, error: [{}]",
					sessionId, e.getMessage());
		}
		return flag;
	}

}