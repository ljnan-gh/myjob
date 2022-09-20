package myjob.core.middleware.kafka.consumer;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumer {
	@KafkaListener(topics = "he")
	public void listen(ConsumerRecord<?,?>record) {
		Optional.ofNullable(record.value()).ifPresent(message->{
			log.info("record:{}",record);
			log.info("message:{}",message);
		});
//		System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
	}
}

