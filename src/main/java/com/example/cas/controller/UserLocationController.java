package com.example.cas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cas.model.Status;
import com.example.cas.model.Users;
import com.example.cas.service.UserService;

@RestController
@RequestMapping(path = "/users")
public class UserLocationController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/api/vi/users/locations", consumes = "application/json")
	public Status saveData(@RequestBody Users user) {
		return userService.saveUser(user);
	}

}
