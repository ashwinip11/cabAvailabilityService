package com.example.cas.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import com.example.cas.model.Cabs;
import com.example.cas.model.GeoLocation;
import com.example.cas.repository.CabRepo;
import com.example.cas.repository.UserRepo;
import com.example.cas.service.CabService;

@RunWith(MockitoJUnitRunner.class)
public class CabLocationControllerTest {
	@InjectMocks
	CabLocationController cabController;
	@Mock
	CabService cabSrvc;

	@Mock
	UserRepo userRepo;

	@Mock
	CabRepo cabRepo;

	@Test
	public void testgetClosestCabs() {

		List<Cabs> listOfCabs = new ArrayList<>();
		Cabs cabs1 = new Cabs();
		cabs1.setCabId("a123");
		cabs1.setDriverId("abc@test.com");
		GeoLocation geo = new GeoLocation();
		geo.setLatitude(11.0);
		geo.setLongitude(11.0);
		geo.setGeoId("gg1234");
		cabs1.setGeoLocation(geo);

		Cabs cabs2 = new Cabs();
		cabs2.setCabId("b123");
		cabs2.setDriverId("xyz@test.com");
		GeoLocation geo2 = new GeoLocation();
		geo2.setLatitude(12.0);
		geo2.setLongitude(12.0);
		geo2.setGeoId("gg456");
		cabs2.setGeoLocation(geo2);

		listOfCabs.add(cabs1);
		listOfCabs.add(cabs2);

		Mockito.when(cabSrvc.getClosestCabs(Mockito.anyString(), Mockito.anyInt())).thenReturn(listOfCabs);

		ResponseEntity<List<Cabs>> data = cabController.getData("cc123", 2);

		assertEquals(2, data.getBody().size());

	}

}
