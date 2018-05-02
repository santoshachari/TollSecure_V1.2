package com.tollsecure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestServiceImpl implements RestService {

	org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
	
	final String ROOT_URI = "https://8h99itgsi9.execute-api.us-west-2.amazonaws.com/prod/vehicleclass?url=http://l7.alamy.com/zooms/628d907018034e7abf3b8367ea2e9e14/decorated-indian-truck-on-a-himalayan-mountain-highway-in-ladakh-india-c66dgb.jpg";
	
	@Override
	public String getVehicleType() {
		
		ResponseEntity<String> response = restTemplate.getForEntity(ROOT_URI, String.class);
		
		return response.getBody();
	}
}