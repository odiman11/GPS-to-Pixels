package convertGpsToPixels;

public class ConverGpsToPixels {
	/*
    this class converts GPS coordinates to XY pixels on screen.
    to determine the X and Y, the main method 'convert', finds the distance between the host and a target object
    and calculate the proper XY coordinates to present them on screen in relations to the 'RANGE_NAUTICAL_MILE' variable (which represents the radius of the screen)
    */
	
	//range in nautical miles that the screen represent
    private static final double RANGE_NAUTICAL_MILE = 20.0;
    // Radius of earth, Use 3956 for miles or 6371 for Km
	private static final double EARTH_RADIUS = 3956;
    
	public double[] convert(double targetLat, double targetLon, double hosetLat, double hostLon, int width, int height) {
    	/*
	    the main method for this class.
	    * PARAM:
	    int width - Width of the parent view/viewgroup/screen.
        int height - Height of the parent view/viewgroup/screen.
        double targetLat - target's  latitude.
        double targetLon - target's longitude.
        double hosetLat - host's  latitude.
        double hostLon - host's  longitude.
	   * RETURN: Float list of X and Y pixels points
	    */
		
		//find the center XY of the screen to represent the host
		int diameter = Math.min(width, height);
		int center = diameter /2; //radius
		double radiusDouble = (double) center;
		
		//longitude and latitude of target and host
		double closeObjectY = targetLat;
		double closetObjectX = targetLon;
    	double hostX = hostLon;
    	double hostY = hosetLat;
    
    	//STEP 1 - find distance in km or miles
    	//convert to radians
    	double lat1 = Math.toRadians(hostY);
    	double lon1 = Math.toRadians(hostX);
    	double lat2 = Math.toRadians(closeObjectY);
    	double lon2 = Math.toRadians(closetObjectX);
    	//find the Radian Difference with Haversine formula(C)
    	double dlon = lon2 - lon1;
    	double dlat = lat2 - lat1;
    	double a = Math.pow(Math.sin(dlat / 2), 2)
    			+ Math.cos(lat1) * Math.cos(lat2)
            	* Math.pow(Math.sin(dlon / 2),2);
    	double c = 2 * Math.asin(Math.sqrt(a));

    	// calculate the result
    	double distance = c * EARTH_RADIUS;
    	
    	//STEP 2 - convert to Minutes to find XY
    	//convert coord to string in format - DDD:MM.SSSS
    	//D indicates degrees, M indicates minutes of arc, and S indicates seconds of arc
    	//(1 minute = 1/60th of a degree, 1 second = 1/3600th of a degree).
    	double[] latMin1 = convertToDoubleList(convertLocation(hostY, "FORMAT_MINUTES").split(":"));
    	double[] latMin2 = convertToDoubleList(convertLocation(closeObjectY, "FORMAT_MINUTES").split(":"));
    	double[] lonMin1 = convertToDoubleList(convertLocation(hostX, "FORMAT_MINUTES").split(":"));
    	double[] lonMin2 = convertToDoubleList(convertLocation(closetObjectX, "FORMAT_MINUTES").split(":"));

    	//find the difference in minutes = nautical mile
    	double latMileY = convertToMapCoord(latMin1, latMin2);
    	double lonMileX = convertToMapCoord(lonMin1, lonMin2);

    	//CONVERT TO SCREEN XY
    	//find the percentage of the difference from radar radius(nautical mile)
    	double percentX = lonMileX / RANGE_NAUTICAL_MILE;
    	double percentY = latMileY / RANGE_NAUTICAL_MILE;

    	double pixelPercX = percentX * radiusDouble;
    	double pixelPercy = percentY * radiusDouble;

    	//find how much is the percent from the radius in pixels
    	double pixelX = radiusDouble + pixelPercX;
    	double pixelY = radiusDouble + (-pixelPercy);

    	//THE RETURN
    	//return array of target X and Y coordinates and distance from host
    	return new double[]{pixelX, pixelY, distance};
		}
	
	//HELPER METHODS             
	private double convertToMapCoord(double[] host, double[] target){
		double degreeHost = (host[0] * 60) + host[1];
		double degreeTarget = (target[0] * 60) + target[1];
		return degreeTarget - degreeHost;
	}

	private double[] convertToDoubleList(String[] coord){
		double[] result = new double[2];
    	int c = 0;
    	for (String n : coord){
    		result[c] = Double.parseDouble(n);
    		c++;
    	}
    	return result;
	}

	private static String convertLocation(double location, String outputMode) {
		switch (outputMode) {
        case "FORMAT_DEGREES":
            return String.format("%s%.6f", location < 0 ? "-" : "", Math.abs(location));
        case "FORMAT_MINUTES":
            int degrees = (int) location;
            double minutes = Math.abs((location - degrees) * 60.0);
            int index = (int)Math.round(minutes * 1000);
            double[] lookupTable = getLookupTable();
            return String.format("%s%03d:%06.3f", location < 0 ? "-" : "", degrees, lookupTable[index]);
        case "FORMAT_SECONDS":
            degrees = (int) location;
            double decimalMinutes = Math.abs((location - degrees) * 60.0);
            int intMinutes = (int) decimalMinutes;
            double seconds = (decimalMinutes - intMinutes) * 60.0;
            return String.format("%s%03d:%02d:%06.3f", location < 0 ? "-" : "", degrees, intMinutes, seconds);
        case "FORMAT_MINUTES_SECONDS":
            degrees = (int) location;
            decimalMinutes = Math.abs((location - degrees) * 60.0);
            intMinutes = (int) decimalMinutes;
            seconds = (decimalMinutes - intMinutes) * 60.0;
            return String.format("%s%03d:%06.3f", location < 0 ? "-" : "", degrees, decimalMinutes);
        default:
            throw new IllegalArgumentException("Invalid output mode.");
		}
	}

	private static double[] getLookupTable() {
		double[] table = new double[21600];
		for (int i = 0; i < table.length; i++) {
			table[i] = i / 60000.0;
		}
		return table;
	}
}