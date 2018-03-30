package myVelibCore.stationPackage;

import myVelibCore.exceptions.UnimplementedSubclassWithoutInputException;
import myVelibCore.userAndCardPackage.Observer;
import myVelibCore.utilities.GPSLocation;

/**
 * The standard station type
 * @author Simon Lecler
 *
 */
public class StationStandard extends Station{
	/**
	 * Credit given to the user card when he returns a bike
	 */
	private final int creditGivenWhenReturning=0;
	/**
	 * Constructor of the standard station
	 * @param gpsLocation
	 * 		The GPS Coordinates of the station
	 * @throws UnimplementedSubclassWithoutInputException 
	 */
	public StationStandard(GPSLocation gpsLocation, Network network) throws UnimplementedSubclassWithoutInputException {
		super(gpsLocation,network);
	}
	@Override
	public void registerObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}

	

}
