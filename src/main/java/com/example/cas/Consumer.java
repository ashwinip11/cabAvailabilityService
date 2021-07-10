package com.example.cas;

import com.example.cas.cfs.CabEntity;
import com.example.cas.model.Cabs;
import com.example.cas.repository.CabRepo;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	@Autowired
	private CabRepo repo;

	private static ObjectMapper mapper = new ObjectMapper();

	@KafkaListener(topics = "cab_location", groupId = "cabLocation")
	public void consumeMessage(String cab) throws JsonParseException, JsonMappingException, IOException {
		if (StringUtils.isNotEmpty(cab)) {
			try {
				//read the cab and map it to json object and save it in the cab entity
				Cabs toSaveObj = mapper.readValue(cab, Cabs.class);
				CabEntity entity = new CabEntity();
				entity.setCabId(toSaveObj.getCabId());
				entity.setDriverId(toSaveObj.getDriverId());
				entity.setLatitude(toSaveObj.getGeoLocation().getLatitude());
				entity.setLongitude(toSaveObj.getGeoLocation().getLongitude());
				entity.setStatus(toSaveObj.getStatus());
				//save it to the cab entity 
				repo.save(entity);
			} catch (Exception ex) {
				System.err.println("Exception at consumer" + ex.getMessage());
			}
		}
	}
}
