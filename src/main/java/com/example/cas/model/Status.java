package com.example.cas.model;

import org.springframework.http.HttpStatus;

public class Status {

	public HttpStatus code;
	public String description;
	public HttpStatus getCode() {
		return code;
	}
	public void setCode(HttpStatus code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Status(HttpStatus code, String description) {
		super();
		this.code = code;
		this.description = description;
	}	
	
	
}
