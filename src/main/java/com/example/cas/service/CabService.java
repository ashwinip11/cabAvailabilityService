package com.example.cas.service;

import com.example.cas.cfs.CabEntity;
import com.example.cas.cfs.UserEntity;
import com.example.cas.model.Cabs;
import com.example.cas.model.GeoLocation;
import com.example.cas.repository.CabRepo;
import com.example.cas.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

@Service
public class CabService {
	@Autowired
	private CabRepo cabRepo;

	@Autowired
	private UserRepo userRepo;
	
	public List<Cabs> getAvailableCabs() throws Exception {
		//get all the available cab list and public them
		Iterable<CabEntity> allList = cabRepo.findAll();
		List<CabEntity> availableList = new ArrayList<>();
		allList.forEach(eachRec -> {
			if (eachRec.getStatus().equalsIgnoreCase("available")) {
				availableList.add(eachRec);
			}
		});
		return populateToCabs(availableList);
	}

	private List<Cabs> populateToCabs(List<CabEntity> availableList) {
		List<Cabs> publishList = new ArrayList<>();
		availableList.forEach(eachRec -> {
			Cabs cab = new Cabs();
			cab.setCabId(eachRec.getCabId());
			cab.setDriverId(eachRec.getDriverId());
			cab.setStatus(eachRec.getStatus());
			GeoLocation geo = new GeoLocation();
			geo.setLatitude(eachRec.getLatitude());
			geo.setLongitude(eachRec.getLongitude());
			cab.setGeoLocation(geo);
			publishList.add(cab);
		});

		return publishList;
	}

	public List<Cabs> getClosestCabs(String customerLocation, Integer cabsRequired) {
		// get the customer coordiantes
		UserEntity customerCoorinates = getCustomerCoordiantes(customerLocation);
		List<Cabs> nearestCabs = new ArrayList<>();
		PriorityQueue<Map.Entry<Double, String>> queue = new PriorityQueue<>(Map.Entry.comparingByValue(Comparator.reverseOrder()));
		if (null != customerCoorinates) {
			double customerLatitude = customerCoorinates.getLatitude();
			double customerLongitude = customerCoorinates.getLongitude();
			Iterable<CabEntity> cabList = getCabsList();
			cabList.forEach(eachRec -> {
				double distance = getDistanceFromCustomer(eachRec.getLatitude(), eachRec.getLongitude(), customerLatitude,
						customerLongitude);
				//queue.offer(new AbstractMap.SimpleEntry<>(eachRec.getCabId(), distance));
				queue.offer(new AbstractMap.SimpleEntry(distance, eachRec.getCabId()));
			//	toList.add(distance);
			});
		}
		for (int i = 0; i < cabsRequired; i++) {
			String top_element = queue.poll().getValue();
			Cabs cab = new Cabs();
			cab.setCabId(top_element);
			nearestCabs.add(cab);
		}
		return nearestCabs;

	}

	private Iterable<CabEntity> getCabsList() {
		return cabRepo.findAll();
	}

	public double getDistanceFromCustomer(double cabx, double caby, double customerx, double customery) {
		// calcualte the distance between two points
		double deltax = cabx - customerx;
		double deltay = caby - customery;
		return Math.sqrt(deltax * deltax + deltay * deltay);

	}

	private UserEntity getCustomerCoordiantes(String customerLocation) {
		Optional<UserEntity> userDetails = userRepo.findById(customerLocation);
		if (userDetails.isPresent()) {
			return userDetails.get();
		}
		return null;
	}
}
