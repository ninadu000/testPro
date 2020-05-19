package com.mtl;

public class FlightSchedule {

	String flight;
	String departure;

	public String getFlight() {
		return flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	@Override
	public String toString() {
		return "flightSchedule [flight=" + flight + ", departure=" + departure + "]";
	}

}
