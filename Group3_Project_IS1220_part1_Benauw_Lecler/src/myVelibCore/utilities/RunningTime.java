package myVelibCore.utilities;

import myVelibCore.stationPackage.Network;

/**
 * <b>This class allows to have a unique thread for the time management</b>
 * <p>It is a singleton pattern. This class implements runnable interface.
 * @author Simon Lecler
 *
 */
public class RunningTime implements Runnable{
	private static RunningTime instance = new RunningTime();
	private Thread currentThread;
	private static boolean isTimeRunning;
	
	private RunningTime() {
		
	}
	/**
	 * 
	 * @return the unique instance of the RunningTime class
	 */
	public static RunningTime getInstance() {
		if (instance==null) {
			instance = new RunningTime();
			}
			return instance;
	}
	/**
	 * Overridden method run from interface runnable
	 * During the simulation, the time is incremented of 1 min at each loop
	 */
	@Override
	public void run() {
		Time clock = new Time();
		while(isTimeRunning) {
			Time.updateTime();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		
		}
	}
	
	public static void runTime() {
		if(RunningTime.isTimeRunning()) {
			System.out.println("Time is already running !");
		}
		else { 
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RunningTime.setTimeRunning(true);
			Thread time = new Thread(RunningTime.getInstance());
			RunningTime.getInstance().setCurrentThread(time);
			time.start();
		}
	}
	
	public static void stopTime() {
		if(RunningTime.isTimeRunning()) {
			RunningTime.getInstance().getCurrentThread().interrupt();
			RunningTime.setTimeRunning(false);
			System.out.println("Running Time Thread interrupted !");
		}
		else {
			System.out.println("Time is already stopped !");
		}
	}
	
	public Thread getCurrentThread() {
		return currentThread;
	}
	public void setCurrentThread(Thread currentThread) {
		this.currentThread = currentThread;
	}
	public static boolean isTimeRunning() {
		return isTimeRunning;
	}
	public static void setTimeRunning(boolean isTimeRunning) {
		RunningTime.isTimeRunning = isTimeRunning;
	}

}
	
	



