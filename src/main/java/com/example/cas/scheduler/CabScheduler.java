package com.example.cas.scheduler;

import com.example.cas.Producer;
import com.example.cas.model.Cabs;
import com.example.cas.model.GeoLocation;
import com.example.cas.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CabScheduler {
	@Autowired
	private CabService cabService;

	@Autowired
	private Producer kafkaProcuder;

	@Scheduled(cron = "* 0/2 * * * ?")
	public void sendCabDetails() throws Exception {
		try {
			// get the list of the available cabs
			
			  List<Cabs> cabsList = cabService.getAvailableCabs(); cabsList.forEach(eachCab
			  -> { Cabs cab = new Cabs(); cab.setCabId(eachCab.getCabId());
			  cab.setDriverId(eachCab.getDriverId()); cab.setStatus(eachCab.getStatus());
			  GeoLocation geo = new GeoLocation();
			  geo.setLatitude(eachCab.getGeoLocation().getLatitude());
			  geo.setLongitude(eachCab.getGeoLocation().getLongitude());
			  cab.setGeoLocation(geo); try { // public the available cabs every 2 sec in cab_location topic 
				  kafkaProcuder.sendMessage(cab); } catch (Exception e) {
			  e.printStackTrace(); } }); 

		} catch (Exception ex) {
			System.err.println("Exception at Scheduler" + ex.getMessage());
		}
	}
}
