package helperClasses;
import java.util.HashMap;
import java.util.Map;

/**
 * Class contains the definitions for the SIPP code letters.
 * HashMap implementations allow easy maintenance and updating.
 * 
 * @author Radu Stefan
 *
 */
public class SippDictionary {

	private Map<Integer, Map<String, String>> sippMap;
	private Map<String, String> carTypeMap;
	private Map<String, String> doorsTypeMap;
	private Map<String, String> transmissionMap;
	private Map<String, String> fuelAirconMap;
	
	/**
	 * Empty Constructor. Initialises maps.
	 */
	public SippDictionary() {
		// Create high level SIPP code map
		this.sippMap = new HashMap<Integer, Map<String, String>>();
		// 4 HashMaps based on the table provided
		this.carTypeMap	     = new HashMap<String, String>();
		this.doorsTypeMap	 = new HashMap<String, String>();
		this.transmissionMap = new HashMap<String, String>();
		this.fuelAirconMap 	 = new HashMap<String, String>();
		
		// Map letters to values for car type
		this.carTypeMap.put("M", "Mini");
		this.carTypeMap.put("E", "Economy");
		this.carTypeMap.put("C", "Compact");
		this.carTypeMap.put("I", "Intermediate");
		this.carTypeMap.put("S", "Standard");
		this.carTypeMap.put("F", "Full size");
		this.carTypeMap.put("P", "Premium");
		this.carTypeMap.put("L", "Luxury");
		this.carTypeMap.put("X", "Special");
		
		// Map letters to values for car type/doors
		this.doorsTypeMap.put("B", "2 doors");
		this.doorsTypeMap.put("C", "4 doors");
		this.doorsTypeMap.put("D", "5 doors");
		this.doorsTypeMap.put("W", "Estate");
		this.doorsTypeMap.put("T", "Convertible");
		this.doorsTypeMap.put("F", "SUV");
		this.doorsTypeMap.put("P", "Pick up");
		this.doorsTypeMap.put("V", "Passenger van");
		this.doorsTypeMap.put("X", "Special");
		
		// Map letters to values for transmission
		this.transmissionMap.put("M", "Manual");
		this.transmissionMap.put("A", "Automatic");
		
		// Map letters to values for fuel/air conditioning
		this.fuelAirconMap.put("N", "Petrol/no AC");
		this.fuelAirconMap.put("R", "Petrol/AC");
		
		// Bring everything together in the high level SIPP map
		this.sippMap.put(0, carTypeMap);
		this.sippMap.put(1, doorsTypeMap);
		this.sippMap.put(2, transmissionMap);
		this.sippMap.put(3, fuelAirconMap);		
	}
	
	/**
	 * Method returns the high level SIPP code map.
	 * @return
	 */
	public Map<Integer, Map<String, String>> getSippMap() {
		return this.sippMap;
	}
}
