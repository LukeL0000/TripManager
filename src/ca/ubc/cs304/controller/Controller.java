package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.Trip;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.TerminalTransactions;
import ca.ubc.cs304.ui.WelcomeStart;

import java.sql.Connection;

/**
 * This is the main controller class that will orchestrate everything.
 */
//Originally also implemented LoginWindowDelegate Interface, but we don't need that anymore.
public class Controller implements LoginWindowDelegate, TerminalTransactionsDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;
	private WelcomeStart welcomeStart = null;
	private static String username = null;
	private static String password = null;

	public Controller() {
		dbHandler = new DatabaseConnectionHandler();
	}
	
	private void start() {
		loginWindow = new LoginWindow();
		loginWindow.showFrame(this);
	}
	
	/**
	 * Unlikely to need this, assume user already logged in.
	 * LoginWindowDelegate Implementation
	 * 
     * connects to Oracle database with supplied username and password
	 */
	public void login(String username, String password) {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
			this.username = username;
			this.password = password;
			// Once connected, remove login window and start text transaction flow, enable UI
			loginWindow.dispose();
			//TerminalTransactions transaction = new TerminalTransactions();
			databaseSetup();
			welcomeStart = new WelcomeStart(dbHandler);
		} else {
			loginWindow.handleLoginFailed();

			if (loginWindow.hasReachedMaxLoginAttempts()) {
				loginWindow.dispose();
				System.out.println("You have exceeded your number of allowed attempts");
				System.exit(-1);
			}
		}
	}

	public Connection getConnection() {
		return dbHandler.getConnection();
	}
	
	/**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Insert a branch with the given info
	 */
    public void insertTrip(Trip model) {
    	dbHandler.insertTrip(model);
    }

    /**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Delete branch with given branch ID.
	 */ 
    public void deleteTrip(String tripID) {
    	dbHandler.deleteTrip(tripID);
    }
    
    /**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Update the trip. more specifications needed.
	 */

/*    public void updateTrip(int branchId, String name) {
    	dbHandler.updateBranch(branchId, name);
    }*/

    /**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Displays information about varies trips figure this out later.
	 */
/*    public void showBranch() {
    	Branch[] models = dbHandler.getBranchInfo();
    	
    	for (int i = 0; i < models.length; i++) {
    		Branch model = models[i];
    		
    		// simplified output formatting; truncation may occur
    		System.out.printf("%-10.10s", model.getId());
    		System.out.printf("%-20.20s", model.getName());
    		if (model.getName() == null) {
    			System.out.printf("%-20.20s", " ");
    		} else {
    			System.out.printf("%-20.20s", model.getName());
    		}
    		System.out.printf("%-15.15s", model.getCity());
    		if (model.getPhoneNumber() == 0) {
    			System.out.printf("%-15.15s", " ");
    		} else {
    			System.out.printf("%-15.15s", model.getPhoneNumber());
    		}
    		
    		System.out.println();
    	}
    }*/
	
    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
     * The TerminalTransaction instance tells us that it is done with what it's 
     * doing, so we are cleaning up the connection since it's no longer needed.
     */ 
    public void terminalTransactionsFinished() {
    	dbHandler.close();
    	dbHandler = null;
    	
    	System.exit(0);
    }
    
    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
     * The TerminalTransaction instance tells us that the user is fine with dropping any existing table
     * called branch and creating a new one for this project to use
     */ 
	public void databaseSetup() {
		dbHandler.databaseSetup();;
	}
    
	/**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		Controller controller = new Controller();
		controller.start();
	}

	public static String getUsername() {
		return username;
	}
	public static String getPassword() {
		return password;
	}
}
