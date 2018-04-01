package myVelibCLUI;

import myVelibCore.abstractFactoryPattern.AbstractFactory;
import myVelibCore.abstractFactoryPattern.FactoryProducer;
import myVelibCore.exceptions.NetworkNameAlreadyUsedException;
import myVelibCore.exceptions.NotEnoughSlotsException;
import myVelibCore.stationPackage.Network;
import myVelibCore.stationPackage.ParkingSlot;
import myVelibCore.stationPackage.Station;
import myVelibCore.userAndCardPackage.Card;
import myVelibCore.userAndCardPackage.User;
import myVelibCore.utilities.GPSLocation;
import myVelibCore.utilities.RunningTime;
import myVelibCore.utilities.Time;

public class ParsingAndCalling {
	
	private static final String TYPE_ERROR_MSG = "An argument is of a wrong type. Please retry or type help for assistance.";
	
	public static void setupWith1Param(String[] args) {
		String velibNetworkName;
		velibNetworkName = args[1];
		try {
			Network.setupNetwork(velibNetworkName, 10, 10, 4, 75);
		}
		
	}
	
	public static void setupWith5Param(String[] args) {
		String name; int nstations=0; int nslots=0; float sidearea=0; int nbikes=0;
		boolean argsParsable = true;
		name = args[1];
		try{nstations = Integer.parseInt(args[2]);}
		catch(Exception e) {System.out.println("Error : nstations must be an integer"); argsParsable = false;}
		try{nslots = Integer.parseInt(args[3]);}
		catch(Exception e) {System.out.println("Error : nslots must be an integer"); argsParsable = false;}
		try{sidearea = Float.parseFloat(args[4]);}
		catch(Exception e) {System.out.println("Error : sidearea must be a float"); argsParsable = false;}
		try{nbikes = Integer.parseInt(args[5]);}
		catch(Exception e) {System.out.println("Error : nbikes must be an integer"); argsParsable = false;}
		if(argsParsable) {
			try {
				Network.setupNetwork(name, nstations, nslots, sidearea, nbikes);
			}
		}
		else {System.out.println(TYPE_ERROR_MSG);}
	}
	
	public static void setupWith6Param(String[] args) {
		String name; int nstations=0; int nslots=0; float sidearea=0; int nbikesMechanical=0; int nbikesElectrical=0;
		boolean argsParsable = true;
		name = args[1];
		try{nstations = Integer.parseInt(args[2]);}
		catch(Exception e) {System.out.println("Error : nstations must be an integer"); argsParsable = false;}
		try{nslots = Integer.parseInt(args[3]);}
		catch(Exception e) {System.out.println("Error : nslots must be an integer"); argsParsable = false;}
		try{sidearea = Float.parseFloat(args[4]);}
		catch(Exception e) {System.out.println("Error : sidearea must be a float"); argsParsable = false;}
		try{nbikesMechanical = Integer.parseInt(args[5]);}
		catch(Exception e) {System.out.println("Error : nbikesMechanical must be an integer"); argsParsable = false;}
		try{nbikesElectrical = Integer.parseInt(args[6]);}
		catch(Exception e) {System.out.println("Error : nbikesElectrical must be an integer"); argsParsable = false;}
		if(argsParsable) {
			try {
				Network.setupNetworkWithDefinedBycicle(name, nstations, nslots, sidearea, nbikesMechanical, nbikesElectrical);
			}
		}
		else {System.out.println(TYPE_ERROR_MSG);}
	}
	
	public static void addUserWith3Param(String[] args) {
		String userName; String cardType; String velibNetworkName;
		userName = args[1];
		cardType = args[2];
		velibNetworkName = args[3];
		try {
			Network network = Network.searchNetworkByName(velibNetworkName);
			AbstractFactory UserFactory = FactoryProducer.getFactory("User");
			User user = UserFactory.getUser(userName,network);
			AbstractFactory cardFactory = FactoryProducer.getFactory("Card");
			Card userCard = cardFactory.getCard(cardType);
			Thread userThread = new Thread(user);
			userThread.start();
		}
	}
	
	public static void setUserGPSPositionWith4Param(String[] args) {
		String velibNetworkName; String userName ; double latitude = 0; double longitude = 0;
		boolean argsParsable = true;
		velibNetworkName = args[1];
		userName = args[2];
		try {latitude = Double.parseDouble(args[3]);}
		catch(Exception e) {System.out.println("Error : latitude must be a double"); argsParsable = false;}
		try {longitude = Double.parseDouble(args[4]);}
		catch(Exception e) {System.out.println("Error : longitude must be a double"); argsParsable = false;}
		if(argsParsable) {
			try {
				Network network = Network.searchNetworkByName(velibNetworkName);
				User user = network.searchUserByName(userName);
				user.setGpsLocation(new GPSLocation(latitude, longitude));
			}
		}
		else {System.out.println(TYPE_ERROR_MSG);}
	}
	
	public static void offlineWith2Param(String[] args) {
		String velibNetworkName; int stationID;
		boolean argsParsable = true;
		velibNetworkName = args[1];
		try{stationID = Integer.parseInt(args[2]);}
		catch(Exception e) {System.out.println("Error : stationID must be an integer"); argsParsable = false;}
		if(argsParsable) {
			try {
				Network network = Network.searchNetworkByName(velibNetworkName);
				Station station = network.searchStationByID(stationID);
				station.turnOff();
			}
		}
		else {System.out.println(TYPE_ERROR_MSG);}
	}
	
	public static void onlineWith2Param(String[] args) {
		String velibNetworkName; int stationID;
		boolean argsParsable = true;
		velibNetworkName = args[1];
		try{stationID = Integer.parseInt(args[2]);}
		catch(Exception e) {System.out.println("Error : stationID must be an integer"); argsParsable = false;}
		if(argsParsable) {
			try {
			Network network = Network.searchNetworkByName(velibNetworkName);
			Station station = network.searchStationByID(stationID);
			station.turnOn();
			}
		}
		else {System.out.println(TYPE_ERROR_MSG);}
	}
	
	public static void rentBikeWith2Param(String[] args) {
		int userID; int stationID; String bycicleType;
		boolean argsParsable = true;
		try{userID = Integer.parseInt(args[1]);}
		catch(Exception e) {System.out.println("Error : userID must be an integer"); argsParsable = false;}
		try{stationID = Integer.parseInt(args[2]);}
		catch(Exception e) {System.out.println("Error : stationID must be an integer"); argsParsable = false;}
		bycicleType = args[3];
		if(argsParsable) {
			try {
				User user = Network.searchUserByIDAllNetworks(userID);
				Station station = Network.searchStationByIDAllNetworks(stationID);
				station.rentABike(user, bycicleType);
			}
		}
		else {System.out.println(TYPE_ERROR_MSG);}
	}
	
	public static void returnBikeWith3Param(String[] args) {
		int userID; int stationID; int time;
		boolean argsParsable = true;
		try{userID = Integer.parseInt(args[1]);}
		catch(Exception e) {System.out.println("Error : userID must be an integer"); argsParsable = false;}
		try{stationID = Integer.parseInt(args[2]);}
		catch(Exception e) {System.out.println("Error : stationID must be an integer"); argsParsable = false;}
		try{time = Integer.parseInt(args[3]);}
		catch(Exception e) {System.out.println("Error : time must be an integer"); argsParsable = false;}
		if(argsParsable) {
			try {
				User user = Network.searchUserByIDAllNetworks(userID);
				Station station = Network.searchStationByIDAllNetworks(stationID);
				station.returnABike(user);
			}
		}
		else {System.out.println(TYPE_ERROR_MSG);}
	}
	
	public static void displayStationWith2Param(String[] args) {
		String velibNetworkName =""; int stationID=0;
		boolean argsParsable = true;
		velibNetworkName = args[1];
		try{stationID = Integer.parseInt(args[2]);}
		catch(Exception e) {System.out.println("Error : stationID must be an integer"); argsParsable = false;}
		if(argsParsable) {
			try {
				Network network = Network.searchNetworkByName(velibNetworkName);
				Station station = network.searchStationByID(stationID);
				station.display();
			}
			
		}
		else {System.out.println(TYPE_ERROR_MSG);}
	}
	
	public static void displayUserWith2Param(String[] args) {
		String velibNetworkName; int userID;
		boolean argsParsable = true;
		velibNetworkName = args[1];
		try{ userID = Integer.parseInt(args[2]);}
		catch(Exception e) {System.out.println("Error : userID must be an integer"); argsParsable = false;}
		if(argsParsable) {
			try {
				Network network = Network.searchNetworkByName(velibNetworkName);
				User user = network.searchUserByID(userID);
				user.display();
			}
		}
		else {System.out.println(TYPE_ERROR_MSG);}
	}
	
	public static void sortStationWith2Param(String[] args) {
		String velibNetworkName; String policy;
		velibNetworkName = args[1];
		policy = args[2];
		try {
			Network network = Network.searchNetworkByName(velibNetworkName);
			network.sortStationBy(policy);
		}
		
	}
	
	public static void displayWith1Param(String[] args) {
		String velibNetworkName;
		try {
			Network network = Network.searchNetworkByName(velibNetworkName);
			System.out.println(network);
		}
		
	}
	
	public static void listNetworkWith0Param(String[] args) {
		Network.ListAllNetworks();
	}
	
	public static void planningRideWith6Param(String[] args) {
		String velibNetworkName; String userName; String policy ; double destinationLatitude ; double destinationLongitude ; String bicycleType;
		boolean argsParsable = true;
		velibNetworkName = args[1];
		userName = args[2];
		policy = args[3];
		try {destinationLatitude = Double.parseDouble(args[4]);}
		catch(Exception e) {System.out.println("Error : destinationLatitude must be a double"); argsParsable = false;}
		try {destinationLongitude = Double.parseDouble(args[5]);}
		catch(Exception e) {System.out.println("Error : destinationLongitude must be a double"); argsParsable = false;}
		bicycleType = args[6];
		if(argsParsable) {
			try {
				GPSLocation destination = new GPSLocation(destinationLatitude, destinationLongitude);
				Network network = Network.searchNetworkByName(velibNetworkName);
				User user = network.searchUserByName(userName);
				user.planningRide(destination, policy, bicycleType);
			}
		}
		else {System.out.println(TYPE_ERROR_MSG);}	
	}
	
	public static void addRentOperationWith5Param (String[] args) {
		String velibNetworkName; int stationID = 0; String userName; int timeOfOperation = 0; int numberOfParkingSlot = 0;
		boolean argsParsable = true;
		velibNetworkName = args[1];
		try {stationID = Integer.parseInt(args[2]);}
		catch(Exception e) {System.out.println("Error : stationID must be an integer"); argsParsable = false;}
		userName = args[3];
		try {timeOfOperation = Integer.parseInt(args[4]);}
		catch(Exception e) {System.out.println("Error : timeOfOperation must be an integer"); argsParsable = false;}
		try {numberOfParkingSlot = Integer.parseInt(args[5]);}
		catch(Exception e) {System.out.println("Error : numberOfParkingSlot must be an integer"); argsParsable = false;}
		try {
			Network network = Network.searchNetworkByName(velibNetworkName);
			User user = network.searchUserByName(userName);
			Station station = network.searchStationByID(stationID);
			ParkingSlot slot = station.getSlots().get(numberOfParkingSlot); //We can do better...
			station.getStationStatitics().addRentOperation(user, new Time(timeOfOperation), slot);
		}
	}
	
	public static void addReturnOperationWith5Param (String[] args) {
		String velibNetworkName; int stationID = 0; String userName; int timeOfOperation = 0; int numberOfParkingSlot = 0;
		boolean argsParsable = true;
		velibNetworkName = args[1];
		try {stationID = Integer.parseInt(args[2]);}
		catch(Exception e) {System.out.println("Error : stationID must be an integer"); argsParsable = false;}
		userName = args[3];
		try {timeOfOperation = Integer.parseInt(args[4]);}
		catch(Exception e) {System.out.println("Error : timeOfOperation must be an integer"); argsParsable = false;}
		try {numberOfParkingSlot = Integer.parseInt(args[5]);}
		catch(Exception e) {System.out.println("Error : numberOfParkingSlot must be an integer"); argsParsable = false;}
		try {
			Network network = Network.searchNetworkByName(velibNetworkName);
			User user = network.searchUserByName(userName);
			Station station = network.searchStationByID(stationID);
			ParkingSlot slot = station.getSlots().get(numberOfParkingSlot); //We can do better...
			station.getStationStatitics().addReturnOperation(user, new Time(timeOfOperation), slot);
		}
	}
	
	public static void runTimeWith0Param(String[] args) {
		if(RunningTime.isTimeRunning()) {
			System.out.println("Time is already running !");
		}
		else { 
			RunningTime.setTimeRunning(true);
			Thread time = new Thread(RunningTime.getInstance());
			RunningTime.getInstance().setCurrentThread(time);
			time.start();
		}
	}
	
	public static void stopTimeWith0Param(String[] args) {
		if(RunningTime.isTimeRunning()) {
			try{RunningTime.getInstance().getCurrentThread().interrupt();}
			catch(SecurityException e) {System.out.println("Running Time Thread interrupted !");
			RunningTime.setTimeRunning(false);
			}
		}
		else {
			System.out.println("Time is already stopped !");
		}
	}
	
}
