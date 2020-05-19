package com.mtl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtl.FlightSchedule;
import com.mtl.service.FlightService;


@RestController

public class FlightController {
	
	@Autowired
	FlightService flightService;
	
	@Value("${diffHour}") 
	private String diffHour;
	
	@RequestMapping("/getFlight/{time}")
	public List<FlightSchedule> getFlight(@PathVariable String time){
		System.out.println("in getFlight");
		return flightService.getFlight(time, diffHour);
	}

	@RequestMapping("/")
	public String home() {
		System.out.println("home");
		return "home.jsp";
	}
}
