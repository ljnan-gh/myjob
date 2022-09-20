package myjob.core.middleware.kafka.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.TopicPartitionInfo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;


@Component
public class KafkaUtil {
	private String skbServices;
	private AdminClient  adminClient;
	@Resource
	private KafkaTemplate<String,String> template;
	private void initAdminClient() {
		Map<String,Object> props = new HashedMap();
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, skbServices);
	
	}
	/**
	 * 新增topic，支持批量
	 */
	public void createTopic(Collection<NewTopic> newTopics) {
		adminClient.createTopics(newTopics);
	}
	/**
	 * 删除topic，支持批量
	 */
	public void deleteTopic(Collection<String> topics) {
		adminClient.deleteTopics(topics);
	}
	/**
	 * 获取指定主题Topic的消息
	 * @return
	 */
	public String getTopicInfo(Collection<String> topics) {
		AtomicReference<String> info = new AtomicReference<String>();
		try {
			adminClient.describeTopics(topics).all().get().forEach((topic,description)->{
				for(TopicPartitionInfo partition:description.partitions()) {
					info.set(info + partition.toString()+"\n");
				}
			});
		} catch (InterruptedException|ExecutionException e) {
			e.printStackTrace();
		} 
		return info.get();
	}
	/**
	 * 获取所有主题topic
	 * @return
	 */
	public List<String> getAllTopic() {
		try {
			return adminClient.listTopics().listings().get().stream().map(TopicListing::name).collect(Collectors.toList());
		} catch (InterruptedException|ExecutionException e) {
			e.printStackTrace();
		} 
		return Lists.newArrayList();
	}
	/**
	 * 往topic中发送消息
	 */
	public void sendMessage(String topic,String message) {
		template.send(topic,message);
	}
}
