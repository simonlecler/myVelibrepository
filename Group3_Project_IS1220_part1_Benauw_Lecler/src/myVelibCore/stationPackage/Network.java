package myVelibCore.stationPackage;

import java.util.ArrayList;
import java.util.Comparator;

import myVelibCore.abstractFactoryPattern.AbstractFactory;
import myVelibCore.abstractFactoryPattern.BycicleFactory;
import myVelibCore.abstractFactoryPattern.FactoryProducer;
import myVelibCore.abstractFactoryPattern.StationFactory;
import myVelibCore.byciclePackage.Bycicle;
import myVelibCore.byciclePackage.BycicleElectrical;
import myVelibCore.byciclePackage.BycicleMechanical;
import myVelibCore.exceptions.BadInstantiationException;
import myVelibCore.exceptions.FactoryNullException;
import myVelibCore.exceptions.NetworkNameAlreadyUsedException;
import myVelibCore.exceptions.NotEnoughSlotsException;
import myVelibCore.exceptions.UnexistingNetworkNameException;
import myVelibCore.exceptions.UnexistingStationIDException;
import myVelibCore.exceptions.UnexistingUserIDException;
import myVelibCore.exceptions.UnexistingUserNameException;
import myVelibCore.exceptions.UnimplementedSubclassWithInputException;
import myVelibCore.exceptions.UnimplementedSubclassWithoutInputException;
import myVelibCore.sortStationPackage.StationComparatorByLeastOccupied;
import myVelibCore.sortStationPackage.StationComparatorByMostUsed;
import myVelibCore.userAndCardPackage.User;
import myVelibCore.utilities.GPSLocation;
import myVelibCore.utilities.IDGenerator;
import myVelibCore.utilities.Time;

public class Network {
	/**
	 * List of all networks
	 */
	private static ArrayList<Network> allNetworks = new ArrayList<Network>();
	/**
	 * Name of the network
	 */
	private String name;
	/**
	 * ID of the network
	 */
	private final int id;
	/**
	 * Stations in the network
	 */
	private ArrayList<Station> allStations = new ArrayList<Station>();
	/**
	 * Standard stations of the network
	 */
	private ArrayList<Station> allStandardStations = new ArrayList<Station>();
	/**
	 * Plus stations of the network
	 */
	private ArrayList<Station> allPlusStations = new ArrayList<Station>();
	/**
	 * Users in the network
	 */
	private ArrayList<User> allUsers = new ArrayList<User>();
	

	/**
	 * 
	 * @return the Id of the network
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param name 
	 * 	The name wanted
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Displays all the networks
	 */
	public static void ListAllNetworks() {
		System.out.println("List of all networks created :");
		for (Network n : allNetworks) {
			System.out.print(n.name + " | ");
		}
		System.out.println("\n");
	}
	/**
	 * Constructor
	 * @param name
	 *      Name of the network
	 * @throws NetworkNameAlreadyUsedException
	 */
	public Network(String name) throws NetworkNameAlreadyUsedException {
		boolean isNameFree = true;
		for (Network n : allNetworks) {
			if (n.getName().equalsIgnoreCase(name)) {
				isNameFree = false;
			}
		}
		if (isNameFree) {
			this.name=name;
			this.id = IDGenerator.getInstance().getNextID();
			allNetworks.add(this);
		}
		else {throw new NetworkNameAlreadyUsedException(name);}
	}
	/**
	 * Set up the network in a given configuration
	 * @param name
	 * @param nStations
	 * @param nSlots
	 * @param sideArea
	 * @param nBikes
	 * @throws NetworkNameAlreadyUsedException
	 * @throws NotEnoughSlotsException
	 */
	public static void setupNetwork(String name, int nStations, int nSlots, double sideArea, int nBikes) throws NetworkNameAlreadyUsedException, NotEnoughSlotsException {
		if (nBikes>nSlots) {throw new NotEnoughSlotsException(nBikes,nSlots);}
		Network network = new Network(name);
		double maxLat = GPSLocation.getMaxLatitude(sideArea);
		double maxLong = GPSLocation.getMaxLongitude(sideArea);
		AbstractFactory stationFactory = null;
		//Creating stations
		try {stationFactory = FactoryProducer.getFactory("Station");} 
		catch (BadInstantiationException e) {System.out.println("This is not supposed to happen " + e.getMessage());}
		for (int i=1; i<=nStations; i++) {
			try {stationFactory.getStation(StationFactory.getRandomStationType(), new GPSLocation(Math.random()*maxLat,Math.random()*maxLong), network);}
			catch (BadInstantiationException | FactoryNullException e) {System.out.println("This is not supposed to happen ! "+e.getMessage());}
		//Adding slots
			for (Station s : network.getAllStations()) {
				for (i=1; i<=nSlots; i++) {
					s.addParkingSlot();
				}
			}
		}
		//Adding Bikes
		Network.addNBikeRandom(network, nBikes);
	}
	/**
	 * Setup the network with defined bicycles
	 * @param name
	 * @param nStations
	 * @param nSlots
	 * @param sideArea
	 * @param nBikesMechanical
	 * @param nBikesElectrical
	 * @throws NetworkNameAlreadyUsedException
	 * @throws NotEnoughSlotsException
	 */
	public static void setupNetworkWithDefinedBycicle(String name, int nStations, int nSlots, double sideArea, int nBikesMechanical, int nBikesElectrical) throws NetworkNameAlreadyUsedException, NotEnoughSlotsException {
		int nBikes = nBikesMechanical+nBikesElectrical;
		if (nBikes>nSlots) {throw new NotEnoughSlotsException(nBikes,nSlots);}
		Network network = new Network(name);
		double maxLat = GPSLocation.getMaxLatitude(sideArea);
		double maxLong = GPSLocation.getMaxLongitude(sideArea);
		AbstractFactory stationFactory = null;
		//Creating stations
		try {stationFactory = FactoryProducer.getFactory("Station");} 
		catch (BadInstantiationException e) {System.out.println("This is not supposed to happen " + e.getMessage());}
		for (int i=1; i<=nStations; i++) {
			try {stationFactory.getStation(StationFactory.getRandomStationType(), new GPSLocation(Math.random()*maxLat,Math.random()*maxLong), network);}
			catch (BadInstantiationException | FactoryNullException e) {System.out.println("This is not supposed to happen ! "+e.getMessage());}
		//Adding slots
			for (Station s : network.getAllStations()) {
				for (i=1; i<=nSlots; i++) {
					s.addParkingSlot();
				}
			}
		}
		//Adding Bikes
		Network.addNBikeRandomWithDefinedType(network,nBikesMechanical,nBikesElectrical);
	}
	
	
	@Override
	public String toString() {
		return("This myVelib Network is situated in" + " "+ name + "\n" + "Its ID is" +" "+ id + "\n" 
				+ "Here are the detailed informations about all the stations of the network :" +  "\n" 
				+ allStations + "\n"
				+ "Here are the detailed informations about all the users of the network" + "\n"
				+ allUsers);		
	}
	
	/**
	 * displays informations about the network
	 */
	public void display() {
		System.out.println(this.name);
		System.out.println(this.id);
		for(User u : this.allUsers) {u.display();}
		for(Station s : this.allStations) {s.display();}
	}


	public String getName() {
		return name;
	}
/**
 * add a user to the network
 * @param user
 */
	public void addUser(User user) {
		this.allUsers.add(user);
	}
	
	/**
	 * 
	 * @return a list of all the users in the network
	 */
	public ArrayList<User> getAllUsers() {
		return allUsers;
	}

/**
 * Add a station to the network
 * @param station
 * @throws UnimplementedSubclassWithoutInputException
 */
	public void addStation(Station station) throws UnimplementedSubclassWithoutInputException {
		if (station instanceof StationStandard) {this.allStandardStations.add(station);this.allStations.add(station);}
		else if (station instanceof StationPlus) {this.allPlusStations.add(station);this.allStations.add(station);}
		else {throw new UnimplementedSubclassWithoutInputException("Station");}
	}
	/**
	 * 
	 * @param stationType
	 * @return a list of all the stations of this type
	 * @throws UnimplementedSubclassWithInputException
	 */
	public ArrayList<Station> getAListOfStationType(String stationType) throws UnimplementedSubclassWithInputException {
		if (stationType.equalsIgnoreCase("All")) {return allStations;}
		else if (stationType.equalsIgnoreCase("Standard")) {return allStandardStations;}
		else if (stationType.equalsIgnoreCase("Plus")) {return allPlusStations;}
		else {throw new UnimplementedSubclassWithInputException("Station",stationType);}
	}
	/**
	 * 
	 * @return all the stations
	 */
	public ArrayList<Station> getAllStations(){return allStations;}
	/**
	 * Add bicycles of a certain type randomly in the network
	 * @param network
	 * @param nBikesMechanical
	 * @param nBikesElectrical
	 * @throws NotEnoughSlotsException
	 */
	public static void addNBikeRandomWithDefinedType(Network network,int nBikesMechanical, int nBikesElectrical) throws NotEnoughSlotsException {
		int totalFreeSlots = 0;
		int nBikes = nBikesMechanical+nBikesElectrical;
		for (Station s : network.getAllStations()) {
			totalFreeSlots = totalFreeSlots + s.getStationBikeCounters().getFreeSlots();
		}
		if (nBikes>totalFreeSlots) {
			throw new NotEnoughSlotsException(nBikes,totalFreeSlots);
		}
		else {
			int totalBikeAdded = 0;
			int totalMechanicalAdded = 0;
			int totalElectricalAdded = 0;
			AbstractFactory bycicleFactory = null;
			try {bycicleFactory = FactoryProducer.getFactory("Bycicle");}
			catch(Exception e) {System.out.println("This is not supposed to happen ! " + e.getMessage());}
			while(totalBikeAdded<nBikes) {
				for (Station s : network.getAllStations()) {
					if(totalBikeAdded<nBikes && Math.random()>0.5) {
						if(totalMechanicalAdded<nBikesMechanical) {
							try {s.addBike(bycicleFactory.getBycicle(BycicleMechanical.typeWritten));}
							catch(Exception e) {System.out.println("This is not supposed to happen !" + e.getMessage());}
							totalBikeAdded++;
							totalMechanicalAdded++;
						}
						else if(totalElectricalAdded<nBikesElectrical) {
							try {s.addBike(bycicleFactory.getBycicle(BycicleElectrical.typeWritten));}
							catch(Exception e) {System.out.println("This is not supposed to happen !" + e.getMessage());}
							totalBikeAdded++;
							totalElectricalAdded++;
						}
					}
				}
			}
		}
	}
	/**
	 * Add bicycles of a certain type randomly in the network
	 */
	
	public static void addNBikeRandom(Network network,int nBikes) throws NotEnoughSlotsException {
		int totalFreeSlots = 0;
		for (Station s : network.getAllStations()) {
			totalFreeSlots = totalFreeSlots + s.getStationBikeCounters().getFreeSlots();
		}
		if (nBikes>totalFreeSlots) {
			throw new NotEnoughSlotsException(nBikes,totalFreeSlots);
		}
		else {
			int totalBikeAdded = 0;
			AbstractFactory bycicleFactory = null;
			try {bycicleFactory = FactoryProducer.getFactory("Bycicle");}
			catch(Exception e) {System.out.println("This is not supposed to happen ! " + e.getMessage());}
			while(totalBikeAdded<nBikes) {
				for (Station s : network.getAllStations()) {
					if(totalBikeAdded<nBikes && Math.random()>0.5) {
						try {s.addBike(bycicleFactory.getBycicle(BycicleFactory.getRandomBycicleType()));}
						catch(Exception e) {System.out.println("This is not supposed to happen !" + e.getMessage());}
						totalBikeAdded++;
					}
				}
			}
		}
	}
	/**
	 * 
	 * @param name
	 * @return the network of this name
	 * @throws UnexistingNetworkNameException
	 */
	public static Network searchNetworkByName(String name) throws UnexistingNetworkNameException {
		for (Network n : allNetworks) {
			if (n.getName().equalsIgnoreCase(name)) {
				return n;
			}
		}
		throw new UnexistingNetworkNameException(name);
	}
	/**
	 * 
	 * @param id
	 * @return the station of this id
	 * @throws UnexistingStationIDException
	 */
	public Station searchStationByID(int id) throws UnexistingStationIDException {
		for (Station s : this.allStations) {
			if (s.getId()== id) {
				return s;
			}
		}
		throw new UnexistingStationIDException(id);
	}
	
	public static Station searchStationByIDAllNetworks(int id) throws UnexistingStationIDException {
		for (Network n : allNetworks) {
			for (Station s : n.allStations) {
				if (s.getId()== id) {
					return s;
				}
			}
		}
		throw new UnexistingStationIDException(id);
	}
	/**
	 * 
	 * @param id
	 * @return the user with this ID
	 * @throws UnexistingUserIDException
	 */
	public User searchUserByID(int id) throws UnexistingUserIDException {
		for (User u : this.allUsers) {
			if (u.getId()== id) {
				return u;
			}
		}
		throw new UnexistingUserIDException(id);
	}
	/**
	 * 
	 * @param name
	 * @return The user with this name
	 * @throws UnexistingUserNameException
	 */
	public User searchUserByName(String name) throws UnexistingUserNameException {
		for (User u : this.allUsers) {
			if (u.getNetwork().equals(name)) {
				return u;
			}
		}
		throw new UnexistingUserNameException(name);
	}
	
	public static User searchUserByIDAllNetworks(int id) throws UnexistingUserIDException {
		for (Network n : allNetworks) {
			for (User u : n.allUsers) {
				if (u.getId()== id) {
					return u;
				}	
			}
		}
		throw new UnexistingUserIDException(id);
	}
	
	public void sortStationByLeastOccupied(Time beginningTime, Time endingTime){
		allStations.sort(new StationComparatorByLeastOccupied(beginningTime,endingTime));
	}
	
	public void sortStationByMostUsed() {
		allStations.sort(new StationComparatorByMostUsed());
	}


	public static ArrayList<Network> getAllNetworks() {
		return allNetworks;
	}


	public static void setAllNetworks(ArrayList<Network> allNetworks) {
		Network.allNetworks = allNetworks;
	}
	

	/**
	 * Sorts the station according a certain policy
	 * @param choice
	 * 	policy wanted
	 * @throws BadInstantiationException
	 */
	public void sortStationBy(String choice) throws BadInstantiationException {
		Comparator<Station> Comparator = null;
		if(choice.equalsIgnoreCase("Least Occupied")) {Comparator = new StationComparatorByLeastOccupied(Time.getOriginalTime(),Time.getCurrentTime());}
		if(choice.equalsIgnoreCase("Most Used")) {Comparator = new StationComparatorByMostUsed();}
		else{throw new BadInstantiationException(choice, "Station Comparator");}
		this.allStations.sort(Comparator);
		System.out.println("Station from the Network " + this.name + "sorted by " + choice);
		for (Station s : this.allStations) {
			s.displayOnlyName();
		}
	}

}
