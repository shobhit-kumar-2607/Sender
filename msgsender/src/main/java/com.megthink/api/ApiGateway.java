package com.megthink.api;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ApiGateway {

	private static final Logger logger = LoggerFactory.getLogger(ApiGateway.class);

	@RequestMapping(value = { "/api/ping", }, method = RequestMethod.GET)
	public String ping() {
		logger.info("Clearing House Core Engine application is up");
		return "Clearing House Core Engine application is up";
	}
}
