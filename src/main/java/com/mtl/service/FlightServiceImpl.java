package com.mtl.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mtl.FlightSchedule;
import com.mtl.util.FlightUtils;

@Component
public class FlightServiceImpl implements FlightService {

	@Override
	public List<FlightSchedule> getFlight(String time, String diffHour) {

		List<FlightSchedule> loadList = FlightUtils.getJsonObj();

		List<FlightSchedule> resultList = new ArrayList<FlightSchedule>();
		if (resultList == null)
			return null;

		for (FlightSchedule fs : loadList) {
			if (FlightUtils.compareTime(time, fs.getDeparture(), Integer.valueOf(diffHour))) {
				resultList.add(fs);
			}
		}
		System.out.println("how many flights can be use: " + resultList.size());
		return resultList;
	}

}
