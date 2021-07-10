package com.example.cas.service;

import com.example.cas.cfs.UserEntity;
import com.example.cas.model.Status;
import com.example.cas.model.Users;
import com.example.cas.repository.UserRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepo repo;

	public Status saveUser(Users user) {
		try {
			UserEntity entity = new UserEntity();
			entity.setCustomerId(user.getCustomerId());
			entity.setGeoId(user.getGeoLocation().getGeoId());
			entity.setLatitude(user.getGeoLocation().getLatitude());
			entity.setLongitude(user.getGeoLocation().getLongitude());
			repo.save(entity);
			LOGGER.info("User entity saved!!");
			return new Status(HttpStatus.CREATED, "User entity saved.");
		} catch (Exception ex) {
			LOGGER.error("Exception while saving customer details" + ex.getMessage());
			return new Status(HttpStatus.INTERNAL_SERVER_ERROR,
					"Exception while saving thr entity for customer ID" + user.getCustomerId());
		}
	}
}


