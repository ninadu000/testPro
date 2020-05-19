package com.mtl.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mtl.FlightSchedule;

@Service
public interface FlightService {

	List<FlightSchedule> getFlight(String time, String diffHour);

}
