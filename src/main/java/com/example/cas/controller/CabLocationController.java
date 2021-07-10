package com.example.cas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cas.model.Cabs;
import com.example.cas.service.CabService;

@RestController
@RequestMapping(path = "api")
public class CabLocationController {
	@Autowired
	private CabService cabService;

	@RequestMapping(value = "/v1/cabs", method = RequestMethod.GET)
	public ResponseEntity<List<Cabs>> getData(@RequestParam String location, @RequestParam Integer cabs) {
		List<Cabs> closestCabs = new ArrayList<>();
		closestCabs = cabService.getClosestCabs(location, cabs);
		return new ResponseEntity<>(closestCabs, HttpStatus.OK);
	}
}
