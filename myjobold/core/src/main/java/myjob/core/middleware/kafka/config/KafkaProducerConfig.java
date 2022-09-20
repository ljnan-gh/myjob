package myjob.core.middleware.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

//    @Value("${kafka.bootstrap.servers}")
    private String BOOTSTRAP_SERVERS_CONFIG = "127.0.0.1:9092";
    @Bean("kafkaTemplate1")
    public KafkaTemplate kafkaTemplate() {
        return new KafkaTemplate<String,String>(producerFactory());
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,String>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(5000);
        return factory;
    }
    @Bean
    public ConsumerFactory<String,String> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }
    @Bean
    public Map<String,Object> consumerConfigs(){
        Map<String,Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,this.BOOTSTRAP_SERVERS_CONFIG);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);//自行控制提交offset
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"100");//提交延迟毫秒数
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"15000");//执行超时时间
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG,"dsafas");
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");//开始消费位置
        return propsMap;
    }
    @Bean
    public ProducerFactory producerFactory() {
        Map props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);

        props.put(ProducerConfig.RETRIES_CONFIG, 0);

        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 4096);

        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 40960);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }


}
