package myjob.core.middleware.kafka.send;

import java.util.Date;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import myjob.core.middleware.kafka.entity.Message;

import javax.annotation.Resource;

@Component
@Slf4j
public class KafkaSender {
	@Resource(name = "kafkaTemplate1")
	private  KafkaTemplate<String, String> kafaTemplate;
	

	public void send(String msg) {
		Message message = new Message();
		message.setId(System.currentTimeMillis());
		message.setMsg(msg);
		message.setSendTime(new Date());
		log.info("");
		kafaTemplate.send("he",msg);
	}
}
