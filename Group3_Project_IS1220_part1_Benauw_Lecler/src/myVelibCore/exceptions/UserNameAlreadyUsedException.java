package myVelibCore.exceptions;

import myVelibCore.stationPackage.Network;

public class UserNameAlreadyUsedException extends Exception{
	private String nameAlreadyUsed;
	private Network network;

	public UserNameAlreadyUsedException(String nameAlreadyUsed, Network network) {
		super();
		this.nameAlreadyUsed = nameAlreadyUsed;
	}
	
	@Override
	public String getMessage() {
		return "The following user name : " + this.nameAlreadyUsed + "is already used in this network. Please pick an other one !";
	}
}
