package com.example.cas.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.example.cas.cfs.UserEntity;
import com.example.cas.model.GeoLocation;
import com.example.cas.model.Status;
import com.example.cas.model.Users;
import com.example.cas.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserLocationControllerTest {

	@InjectMocks
	UserLocationController usrLocationController;
	@Mock
	UserService usrSrvc;

	@Test
	public void testUserService() {
		Users user = new Users();
		user.setCustomerId("cc123");
		GeoLocation geo = new GeoLocation();
		geo.setLatitude(8.0);
		geo.setLongitude(8.0);
		geo.setGeoId("gg123");
		user.setGeoLocation(geo);

		UserEntity userEntity = new UserEntity();
		userEntity.setCustomerId("cc123");
		userEntity.setLatitude(8.0);
		userEntity.setLongitude(8.0);
		userEntity.setGeoId("gg123");

		Status status = new Status(HttpStatus.CREATED, "User entity saved.");
		Mockito.when(usrSrvc.saveUser(Mockito.any(Users.class))).thenReturn(status);
		Status cabStatus = usrLocationController.saveData(user);
		assertEquals(cabStatus, status);
	}
}
