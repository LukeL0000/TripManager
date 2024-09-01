package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.Trip;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case Bank).
 * 
 * TerminalTransactions calls the methods that we have listed below but 
 * Bank is the actual class that will implement the methods.
 */
public interface TerminalTransactionsDelegate {
	public void databaseSetup();
	public void deleteTrip(String tripID);
	public void insertTrip(Trip model);
	/**
	 * THIS IS A FUNCTION FOR SHOWING TRIP INFO IN CONTROLLER. RESTORE IF NECESSARY.
	 */
	//public void showBranch();

	/**
	 * THIS SHOULD BE UPDATE TRIP, CHANGE OR REMOVE.
	 */
	//public void updateBranch(int branchId, String name);
	public void terminalTransactionsFinished();
}
