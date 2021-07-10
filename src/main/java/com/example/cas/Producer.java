package com.example.cas;

import com.example.cas.model.Cabs;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Configuration
public class Producer {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private static final String TOPIC = "cab_location";

	public String sendMessage(Cabs cab) throws Exception {
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Inclusion.NON_NULL);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			mapper.setDateFormat(sdf);
			String jsonString = mapper.writeValueAsString(cab);
			// publish the cab as a json string to the kafka topic cab_location
			kafkaTemplate.send(TOPIC, jsonString);
		} catch (Exception ex) {
			System.err.println("Exception while sending message at producer" + ex.getMessage());
		}
		return "published";
	}
}
