package com.bd.controller;

import io.swagger.annotations.Api;
import myjob.core.middleware.kafka.send.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "Kafka", tags = {"Kafka"})
@RequestMapping("/kafka/")
@RestController
public class KafkaController {
	@Autowired
	private KafkaSender kafkaSender;
	
	@RequestMapping(value = "sendMessage", method = RequestMethod.GET)
	public void sendMessage(String msg) {
		kafkaSender.send(msg);
	}
}
