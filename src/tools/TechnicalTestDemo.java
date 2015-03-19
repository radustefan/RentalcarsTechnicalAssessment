package tools;
import helperClasses.SippDictionary;
import helperClasses.Vehicle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Demo class of the Rentalcars.com graduate technical test tasks.
 * Using JSON.simple library to encode/decode JSON (json-simple-1.1.1.jar)
 * 
 * @author Radu Stefan
 *
 */
public class TechnicalTestDemo {
	
	public static void main (String[] args) {
	
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		try {
			// Read vehicles.json file from TestResources folder
			// FileReader reader = new FileReader("TestResources/vehicles.json");
			// or straight from URL
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://www.rentalcars.com/js/vehicles.json").openStream()));
			JSONParser jsonParser = new JSONParser();
			
			// Get parser and json objects
			JSONObject jsonObject 	= (JSONObject) jsonParser.parse(reader);
			JSONObject vehiclesList = (JSONObject) jsonObject.get("Search");
			JSONArray  jsonVehicles = (JSONArray)  vehiclesList.get("VehicleList");
			
			// Iterate through vehicles and store them in object array
			Iterator<?> iter = jsonVehicles.iterator();
			while (iter.hasNext()) {
				JSONObject jsonVehicle = (JSONObject) iter.next();
				Vehicle vehicle = new Vehicle();
				
				vehicle.setSipp(jsonVehicle.get("sipp").toString());
				vehicle.setName(jsonVehicle.get("name").toString());
				vehicle.setPrice(Double.parseDouble(jsonVehicle.get("price").toString()));
				vehicle.setSupplier(jsonVehicle.get("supplier").toString());
				vehicle.setRating(Double.parseDouble(jsonVehicle.get("rating").toString()));
				
				vehicles.add(vehicle);	
			}
			
			// Assignment tasks implemented as separate methods
			// to separate from original file data
			task1(vehicles);
			task2(vehicles);
			task3(vehicles);
			task4(vehicles);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/** 
	 * TASK 1.
	 * Print a list of all the cars, in ascending price order in following format:
	 * 1. {Vehicle name} - {Price}
	 * 
	 * @param vehicles
	 */
	private static void task1(List<Vehicle> vehicles) {
		
		// Sort ArrayList of vehicles using custom price comparator
		Collections.sort(vehicles, new Comparator<Vehicle>() {
			public int compare(Vehicle v1, Vehicle v2) {
				if (v1.getPrice() < v2.getPrice())
					return -1;
				else if (v1.getPrice() > v2.getPrice())
					return 1;
				else 
					return 0;
			}	
		});
		System.out.println("*************** TASK 1 ********************"); 
		System.out.println("List of all cars in ascending price order:");
		System.out.println("-------------------------------------------");
		for (int i=0; i<vehicles.size(); i++) {
			Vehicle vehicle = vehicles.get(i);
			System.out.println(i+1 + ". {" + vehicle.getName() + "} - {" + vehicle.getPrice() + "}");
		}
	}
	
	/**
	 * TASK 2.
	 * Calculate the specification of the vehicles based on SIPP code. Print in following format:
	 * 1. {Vehicle name} - {SIPP} - {Car type} - {Car type/doors} - {Transmission} - {Fuel} - {Air conditioning}
	 * 
	 * @param vehicles
	 */
	private static void task2(List<Vehicle> vehicles) {
		// Instantiate the SIPP code dictionary and get the SIPP code HashMap
		Map<Integer, Map<String, String>> sippMap = new SippDictionary().getSippMap();
		
		System.out.println("\r\n******************* TASK 2 *********************"); 
		System.out.println("Specifications of vehicles based on their SIPP:");
		System.out.println("------------------------------------------------");
		for (int i=0; i<vehicles.size(); i++) {
			Vehicle vehicle = vehicles.get(i);
						
			System.out.println(i+1 + ". {" + vehicle.getName() + "} -" +
									  " {" + vehicle.getSipp() + "} -" + 
									  // SIPPmap.get(letter index).get(SIPP_letter)
									  " {" + sippMap.get(0).get(vehicle.getSipp().substring(0, 1)) + "} -" +
									  " {" + sippMap.get(1).get(vehicle.getSipp().substring(1, 2)) + "} -" +
									  " {" + sippMap.get(2).get(vehicle.getSipp().substring(2, 3)) + "} -" +
									  // Separate Fuel and Air conditioning
									  " {" + sippMap.get(3).get(vehicle.getSipp().substring(3, 4)).split("/")[0] + "} -" +
									  " {" + sippMap.get(3).get(vehicle.getSipp().substring(3, 4)).split("/")[1] + "}");
		}
	}
	
	/**
	 * TASK 3.
	 * Print out the highest rated supplier per car type, in descending order, in following format:
	 * 1. {Vehicle name} - {Car type} - {Supplier} - {Rating}
	 * 
	 * @param vehicles
	 */
	private static void task3(List<Vehicle> vehicles) {
		double maxRating;
		Map<Integer, Map<String, String>> sippMap = new SippDictionary().getSippMap();
		//Map will hold car_type and list of vehicles of that type
		Map<String, List<Vehicle>> typeVehicleMap = new HashMap<String, List<Vehicle>>();
		for (Vehicle vehicle : vehicles) {
			// If the current car_type does not exist, create a new list for it
			if (!typeVehicleMap.containsKey(vehicle.getSipp().substring(0, 1))) 
				typeVehicleMap.put(vehicle.getSipp().substring(0, 1), new ArrayList<Vehicle>());
			
			// add current vehicle to its corresponding type 
			typeVehicleMap.get(vehicle.getSipp().substring(0, 1)).add(vehicle);
		}
		
		Iterator<Map.Entry<String, List<Vehicle>>> iter = typeVehicleMap.entrySet().iterator();
		// for each category, 1st loop: determine supplier max rating
		//					  2nd loop: remove extra entries (i.e. lower than max rating)
		while (iter.hasNext()) {
			maxRating = 0;
			Map.Entry<String, List<Vehicle>> vehicleEntry = iter.next();
			List<Vehicle> vehicleList = vehicleEntry.getValue();
			for (int i=0; i<vehicleList.size(); i++) {
				if (vehicleList.get(i).getRating() > maxRating)
					maxRating = vehicleList.get(i).getRating();
			}
			
			int temp = vehicleList.size(); // current number of vehicles
			for (int i=0; i<temp; i++) {
				if (vehicleList.get(i).getRating() < maxRating) {
					vehicleList.remove(i);
					// upon removal, decrease number of vehicles and set index back one position
					temp --;
					i --;
				}
			}
		}
		
		System.out.println("\r\n*********************** TASK 3 ************************");
		System.out.println("Highest rated supplier per car type, descending order:");
		System.out.println("-------------------------------------------------------");
		int c = 1; // counter, for numbered list display
		for (Map.Entry<String, List<Vehicle>> vehicleEntry : typeVehicleMap.entrySet()) {
			List<Vehicle> vehicleList = vehicleEntry.getValue();
			for (int i=0; i<vehicleList.size(); i++) {
				System.out.println(c + ". {" + vehicleList.get(i).getName() + "} - " +
										 "{" + sippMap.get(0).get(vehicleEntry.getKey()) + "} - " +
										 "{" + vehicleList.get(i).getSupplier() + "} - " +
										 "{" + vehicleList.get(i).getRating() + "}");
				c++;
			}
		}
	}
	
	/**
	 * TASK 4.
	 * Give each vehicle a score based on given breakdown. Combine the score with supplier rating.
	 * Print a list of vehicles, ordered by the sum of the scores in descending order, in following format:
	 * 1. {Vehicle name} – {Vehicle score} – {Supplier rating} – {Sum of scores}
	 * 
	 * @param vehicles
	 */
	private static void task4(List<Vehicle> vehicles) {
		// map vehicles to corresponding scores
		// two map implementation required to get unique keys
		Map<Integer, Map<Double, Vehicle>> vehicleScoresI = new HashMap<Integer, Map<Double, Vehicle>>();
		// for each vehicle, calculate score
		for (int i=0; i<vehicles.size(); i++) {
			Map<Double, Vehicle> vehicleScores = new HashMap<Double, Vehicle>();
			int score = 0;
			// manual transmission adds 1 point, automatic adds 5 points to score
			if (vehicles.get(i).getSipp().substring(2, 3).equals("M"))
				score += 1;
			else 
				score += 5;
			// air conditioning adds 2 points to score
			if (vehicles.get(i).getSipp().substring(3, 4).equals("R"))
				score += 2;
			// add entry to vehicle-score map
			vehicleScores.put(score + vehicles.get(i).getRating(), vehicles.get(i));
			vehicleScoresI.put(i, vehicleScores);
		} 
	
		/* Sort Vehicles based on their descending sums of scores.
		/* Using 2 for loops, compare two consecutive objects, and swap place in map 
		 * if first object is greater than the second (i.e. for descending order).
		 */  
		for (int i=0; i<vehicleScoresI.keySet().size()-1; i++)
			for (int j=i+1; j<vehicleScoresI.keySet().size(); j++)
				if (vehicleScoresI.get(j).keySet().iterator().next() > vehicleScoresI.get(i).keySet().iterator().next()) {
					Map<Double, Vehicle> tempEntry = vehicleScoresI.get(i);
					vehicleScoresI.replace(i, vehicleScoresI.get(j));
					vehicleScoresI.replace(j, tempEntry);
				}
		
		Iterator<Map.Entry<Integer, Map<Double, Vehicle>>> iter = vehicleScoresI.entrySet().iterator();
		int i=0;
		System.out.println("\r\n********************* TASK 4 ***********************"); 
		System.out.println("List of all cars in descending sum of scores order:");
		System.out.println("----------------------------------------------------");
		while (iter.hasNext()) {
			i ++;
			Map.Entry<Integer, Map<Double, Vehicle>> mapEntryI = iter.next();
			Map<Double, Vehicle> vehicleScores = mapEntryI.getValue();
			Map.Entry<Double, Vehicle> mapEntry = vehicleScores.entrySet().iterator().next();
			System.out.println(i + ". {" + mapEntry.getValue().getName() + "} - " +
									// Subtract rating from sum of scores to get vehicle score and limit to 2 decimals, removing ".0" 
									 "{" + new BigDecimal(mapEntry.getKey() - mapEntry.getValue().getRating()).setScale(2, RoundingMode.HALF_UP).toString().substring(0, 3).replace(".0", "") + "} - " +
									 "{" + new BigDecimal(mapEntry.getValue().getRating()).setScale(2, RoundingMode.HALF_UP).toString().substring(0, 3).replace(".0", "") + "} - " +
									 "{" + new BigDecimal(mapEntry.getKey()).setScale(2, RoundingMode.HALF_UP).toString().substring(0, 4).replace(".0", "") + "}");
		}
	}
}
