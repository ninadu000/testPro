package com.mtl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mtl.FlightSchedule;

public final class FlightUtils {
	
	public static boolean compareTime(String inputTimeStr, String flightTimeStr, int diff){
		
		DateTimeFormatter formatTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
		
		int inputHour = Integer.parseInt(inputTimeStr.split(":")[0]);
		int inputMin = Integer.parseInt(inputTimeStr.split(":")[1].substring(0, 2));
		if(inputTimeStr.endsWith("PM") && !inputTimeStr.startsWith("12")){
			inputHour = inputHour + 12;
		}
		LocalTime inputTime = LocalTime.of(inputHour, inputMin);
		
		
		int flightHour = Integer.parseInt(flightTimeStr.split(":")[0]);
		int flightMin = Integer.parseInt(flightTimeStr.split(":")[1].substring(0, 2));
		if(flightTimeStr.endsWith("PM") && !flightTimeStr.startsWith("12")){
			flightHour = flightHour + 12;
		}
		
		LocalTime flightTime = LocalTime.of(flightHour, flightMin);
		
		LocalTime deadLineTime1 = inputTime.minusHours(diff); // front
		LocalTime deadLineTime2 = inputTime.plusHours(diff); // end
		
		LocalTime md = LocalTime.MIDNIGHT;
		LocalTime ONE_MINUTE_BEFORE_MIDNIGHT = LocalTime.of(23, 59);
		
		boolean result = false;
		
		if(inputTimeStr.endsWith("AM")) {
			if(deadLineTime1.isBefore(ONE_MINUTE_BEFORE_MIDNIGHT)) { //cross midnight
				if(flightTime.isAfter(deadLineTime1) || flightTime.isBefore(deadLineTime2)) {
					System.out.println("flightTime1 = " + flightTime + " is ok");
					result = true;
				}
			}else {
				//normal
				if(flightTime.isAfter(deadLineTime1) && flightTime.isBefore(deadLineTime2)) {
					System.out.println("flightTime2 = " + flightTime + " is ok");
					result = true;
				}
			}			
		}else if(inputTimeStr.endsWith("PM")) {
			if(deadLineTime2.isAfter(ONE_MINUTE_BEFORE_MIDNIGHT)) { //cross midnight
				if(flightTime.isAfter(deadLineTime1) || flightTime.isBefore(deadLineTime2)) {
					System.out.println("flightTime3 = " + flightTime + " is ok");
					result = true;
				}				
			}else {
				//normal
				if(flightTime.isAfter(deadLineTime1) && flightTime.isBefore(deadLineTime2)) {
					System.out.println("flightTime4 = " + flightTime + " is ok");
					result = true;
				}
			}			
		}
		
		return result;
		
	}
	
	
	
	public static String getAMorPMFormatTime(String time, String format){
        SimpleDateFormat formatTime = new SimpleDateFormat(format + " aa", Locale.ENGLISH);
        try {
            return formatTime.format(new SimpleDateFormat(format).parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
	
	
    public static void main(String[] args) {
//        System.out.println(getAMorPMFormatTime("2019-03-03 19:10:10", "yyyy-MM-dd hh:mm:ss"));
//        System.out.println(getTimeByAMorPMFormat("2019-03-03 07:10:10 PM", "yyyy-MM-dd hh:mm:ss"));
//        System.out.println(compareTime("8:10PM","10:20PM",1));
    		getJsonObj();
//        DateTimeFormatter formatter =
//                DateTimeFormatter
//                        .ofPattern("MMM dd, yyyy - HH:mm");
//        LocalDateTime parsed = LocalDateTime.parse("Nov 03, 2014 - 07:13", formatter);
//        String string = formatter.format(parsed);
//        System.out.println(string);     // Nov 03, 2014 - 07:13
//
//        LocalTime localTime= LocalTime.of(23, 59, 59);
//        System.out.println(localTime);    // 23:59:59
//        DateTimeFormatter germanFormatter =
//          DateTimeFormatter
//            .ofLocalizedTime(FormatStyle.SHORT)
//            .withLocale(Locale.GERMAN);
//        String leetTime = localTime.format(germanFormatter);
//        System.out.println(leetTime);  
    }
    
  //Java 8 - Read file line by line - Files.lines(Path path, Charset cs)
    
  
    
    
    public static String getTimeByAMorPMFormat(String time, String format){
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.ENGLISH);
        SimpleDateFormat formatTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = "";
        try {
            str = formatTime1.format(formatTime.parse(time));
        } catch (Exception e) {
            System.out.println("传入日期错误");
        }
        return str;
    }
    
    /**
     * read File.json
     * @param fileName
     * @return String(json)
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static List<FlightSchedule> getJsonObj() {
        String path = FlightUtils.class.getClassLoader().getResource("test.json").getPath();
        String s = readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);
        
//        System.out.println("name"+jobj.get("name"));
//        JSONObject address1 = jobj.getJSONObject("address");
//        String street = (String) address1.get("street");
//        String city = (String) address1.get("city");
//        String country = (String) address1.get("country");
//
//        System.out.println("street :" + street);
//        System.out.println("city :" + city);
//        System.out.println("country :" + country);

        JSONArray flights = jobj.getJSONArray("flights");
        
        List<FlightSchedule> loadList = new ArrayList<FlightSchedule>();

        for (int i = 0 ; i < flights.size();i++){
        	FlightSchedule fSchedule = new FlightSchedule();
            JSONObject key1 = (JSONObject)flights.get(i);
            String flight = (String)key1.get("flight");
            String departure = (String)key1.get("departure");
            System.out.println(flight);
            System.out.println(departure);
            
            fSchedule.setFlight(flight);
            fSchedule.setDeparture(departure);
            loadList.add(fSchedule);
        }
        return loadList;
    }
    
}
