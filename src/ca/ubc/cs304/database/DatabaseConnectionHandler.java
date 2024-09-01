package ca.ubc.cs304.database;

import java.sql.*;
import java.util.ArrayList;

import ca.ubc.cs304.controller.Controller;
import ca.ubc.cs304.model.GenerateID;
import ca.ubc.cs304.model.Location;
import ca.ubc.cs304.model.People;
import ca.ubc.cs304.model.Trip;
import ca.ubc.cs304.util.PrintablePreparedStatement;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
    //	  private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	//	  private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private GenerateID randID = new GenerateID();
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;

	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public Connection getConnection() {
		if (connection == null) {
			login(Controller.getUsername(), Controller.getPassword());
			return connection;
		} else {
			return connection;
		}
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public Trip findTrip(String tripID) {
		try {
			String query = "SELECT * FROM Trip WHERE TripID = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, tripID);
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				Trip trip = new Trip(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getDouble(5), rs.getDouble(6));
				rs.close();
				ps.close();
				return trip;
			} else{
				rs.close();
				ps.close();
				return null;
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return null;
		}
	}

	public void insertTrip(Trip tripID) {
		try {
			String query = "INSERT INTO Trip VALUES (?,?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, tripID.getTripName());
			ps.setString(2, tripID.getTripID());
			ps.setString(3, tripID.getOrganizerID());
			ps.setString(4, tripID.getLocationName());
			ps.setDouble(5, tripID.getLatitude());
			ps.setDouble(6, tripID.getLongitude());

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	/**
	 * TODO
	 * RETURN BOOLEAN IF THE TRIP CAN BE DELETED OR NOT
	 * @param tripID
	 */
	public boolean deleteTrip(String tripID) {
		try {
			String query = "DELETE FROM trip WHERE tripID = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, tripID);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Trip " + tripID + " does not exist!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return false;
	}

	public void addEvent(String tripID, String activity, String conditionID) {
		try {
			String Update = "INSERT INTO TripEvent VALUES (?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setString(1, tripID);
			ps.setString(2, activity);
			ps.setString(3, conditionID);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertPerson(String name, String userID) {
		try {
			String Update = "INSERT INTO People VALUES (?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setString(2, userID);
			ps.setString(1, name);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public ArrayList<People> findPeople(String name, String userID) {
		ArrayList<People> people = new ArrayList<>();
		try {
			String query = "SELECT * FROM People";
			if(userID != null) {
				if(userID != "") {
					query = query + " WHERE UserID = ?";
				}
			} else if(name != null) {
				if(name != "") {
					query = query + " WHERE Name = ?";
				}
			}
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			if(userID != null) {
				ps.setString(1, userID);
			} else if(name != null) {
					ps.setString(1, name);
			}

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				People person = new People(rs.getString(1), rs.getString(2));
				people.add(person);
			}
			rs.close();
			ps.close();
			return people;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return people; //would be null
		}
	}

	//Inserts UserID, TripID to GoOn relation. This signs up the user for a trip.
	public void goOnTrip(People person) {
		try {
			String Update = "INSERT INTO GoOn VALUES (?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setString(1, person.getUserID());
			ps.setString(2, tempTrip.getTripID());

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void takeTo(String userID, String equipmentID, String fromName, double fromLatitude, double fromLongitude,
					   String toName, double toLatitude, double toLongitude) {
		try {
			String Update = "INSERT INTO TakeTo VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setString(1, userID);
			ps.setString(2, equipmentID);
			ps.setString(3, fromName);
			ps.setDouble(4, fromLatitude);
			ps.setDouble(5, fromLongitude);
			ps.setString(6, toName);
			ps.setDouble(7, toLatitude);
			ps.setDouble(8, toLongitude);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void bring(String userID, String equipmentID, int quantity) {
		try {
			String Update = "INSERT INTO Bring VALUES (?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setString(1, userID);
			ps.setString(2, equipmentID);
			ps.setInt(3, quantity);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void certifyUser(String userID, String certification) {
		try {
			String Update = "INSERT INTO QualifiesFor VALUES (?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setString(1, userID);
			ps.setString(2, certification);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void linesUp(Timestamp startTime, Timestamp endTime, String userID, String tripID, String conditionID) {
		try {
			String Update = "INSERT INTO LinesUp VALUES (?, ?, ?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setTimestamp(1, startTime);
			ps.setTimestamp(2, endTime);
			ps.setString(3, userID);
			ps.setString(4, tripID);
			ps.setString(5, conditionID);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void applyConditions(String locationName, double latitude, double longtitude, String conditionID) {
		try {
			String Update = "INSERT INTO Has VALUES (?, ?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setString(1, locationName);
			ps.setDouble(2, latitude);
			ps.setDouble(3, longtitude);
			ps.setString(4, conditionID);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void addRequirement(String activityName, String conditionID, String equipmentID, String certification) {
		try {
			String Update = "INSERT INTO Has VALUES (?, ?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setString(1, activityName);
			ps.setString(2, conditionID);
			ps.setString(3, equipmentID);
			ps.setString(4, certification);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	/**
	 * TODO
	 * RETURN TRUE UPON SUCCESS; FALSE OTHERWISE
	 * @param conditionID
	 * @param activity
	 */
	public boolean recommendActivity(String conditionID, String activity) {
		try {
			String Update = "INSERT INTO Recommends VALUES (?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setString(1, conditionID);
			ps.setString(2, activity);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return true;
	}

	public void addActivityCost(String activity, String conditionID, double cost) {
		try {
			String Update = "INSERT INTO ActivityCost VALUES (?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setString(1, activity);
			ps.setString(2, conditionID);
			ps.setDouble(3, cost);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public Location findLocation(String name, double latitude, double longitude) {
		try {
			String query = "SELECT * FROM Location WHERE Name = ? AND Latitude = ? AND Longitude = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, name);
			ps.setDouble(2, latitude);
			ps.setDouble(3, longitude);
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				Location location = new Location(rs.getString(1), rs.getDouble(2), rs.getDouble(3));
				rs.close();
				ps.close();
				return location;
			} else{
				rs.close();
				ps.close();
				return null;
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return null;
		}
	}

	public void insertLocation(Location location) {
		try {
			String Update = "INSERT INTO Location VALUES (?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(Update), Update, false);
			ps.setString(1, location.getLocationName());
			ps.setDouble(2, location.getLatitude());
			ps.setDouble(3, location.getLongitude());

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}


	/**
	 * THIS SHOULD BE getTripInfo(), WILL WORK ON LATER.
	 */
/*	public Branch[] getBranchInfo() {
		ArrayList<Branch> result = new ArrayList<Branch>();

		try {
			String query = "SELECT * FROM branch";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				Branch model = new Branch(rs.getString("branch_addr"),
						rs.getString("branch_city"),
						rs.getInt("branch_id"),
						rs.getString("branch_name"),
						rs.getInt("branch_phone"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new Branch[result.size()]);
	}*/

	public boolean updateTrip(String newName, String tripID, Location location) {
		try {
			String query = "UPDATE Trip SET ";
			String setvals = "";
			String end =  "WHERE TripID = ?";
			if(newName != null) {
				setvals = "Name = " + "\'" + newName + "\'" + " ";
				if(location != null) {
					setvals += ",";
				}
			} if(location != null) {
				setvals = setvals + " LocationName = " + "\'" + location.getLocationName() + "\'"+ ", " +
						"Latitude = " + location.getLatitude() + ", " + "Longitude = " + location.getLongitude() + " ";
			}

			PrintablePreparedStatement ps = new PrintablePreparedStatement(
					connection.prepareStatement(query+setvals+end), query+setvals+end,
					false);
			ps.setString(1, tripID);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Trip " + tripID + " does not exist!");
			}

			connection.commit();

			ps.close();
			return  true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			return false;
		}
	}

	/**
	 * This connects to the database using user credentials.
	 */
	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}

			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);
			System.out.println(connection);
			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	private void rollbackConnection() {
		try  {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void databaseSetup() {
		//dropAllTables();

		/**
		 * THIS SECTION OF CODE IS FOR INSERTING ALL DATA INTO THE DATABASE.
		 */
/*		try {
			String query = "CREATE TABLE branch (branch_id integer PRIMARY KEY, branch_name varchar2(20) not null, branch_addr varchar2(50), branch_city varchar2(20) not null, branch_phone integer)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}*/

		/**
		 * INSERT DATA HERE
		 */
/*		Branch branch1 = new Branch("123 Charming Ave", "Vancouver", 1, "First Branch", 1234567);
		insertTrip(branch1);

		Branch branch2 = new Branch("123 Coco Ave", "Vancouver", 2, "Second Branch", 1234568);
		insertTrip(branch2);*/
	}

	private void dropAllTables() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();
			String drop = "DROP TABLE ";
			ArrayList<String> dropCommands = new ArrayList<>();

			//preps drop commands
			while(rs.next()) {
				String table_name = rs.getString(1);
				System.out.println(table_name);
				if(!table_name.toLowerCase().equals("")) {
					dropCommands.add(drop + table_name + " cascade constraints");
				} else {
					break;
				}
			}
			//executes drop commands
			for(int i = 0; i < dropCommands.size(); i++) {
				ps.executeUpdate(dropCommands.get(i));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	/**
	 * INPUT: USER NAME, USER ID
	 * OUTPUT: VOID
	 * FUNCTION: CHECKS IF USER NAME, USER ID EXISTS IN DATABASE. IF YES: SET AS TRIP ORGANIZER. ELSE: ADD AS PERSON, SET AS ORGANIZER
	 */

	/**
	 * INPUT: USER NAME, USER ID
	 * OUTPUT: INT
	 * FUNCTION: CHECKS IF USER NAME, USER ID EXISTS IN DATABASE. IF YES: SET AS TRIP ORGANIZER. ELSE: ADD AS PERSON, SET AS ORGANIZER
	 * 0: THE USER WAS SUCCESSFULLY ADDED
	 * 1: THE USER COULD NOT BE ADDED
	 * 2: THE USER ALREADY EXISTS
	 */

	private Trip tempTrip;
	public int setTripOrganizer(String userName, String userID) {
		String newID = userID;
		String newTripID = randID.getID(32);;
		boolean uniqueID = false;
		//creates person with unique ID
		while(!uniqueID) {
			if(findPeople(userName, newID).size() == 0) { //empty list
				uniqueID = false;
				break;
			} else {
				newID = randID.getID(32);
			}
		}
		People organizer = new People(userName, newID);
		insertPerson(userName, newID);

		//create trip with unique ID
		while(!uniqueID) {
			if(findTrip(newTripID) == null) {
				break;
			} else {
				newTripID = randID.getID(32);
			}
		}
		Location tempLocation = new Location("null", 0, 0);
		insertLocation(tempLocation);
		tempTrip = new Trip("null", randID.getID(32), newID, "null", 0, 0);
		insertTrip(tempTrip);

		//have organizer go on trip
		goOnTrip(organizer);
		return 0;
	}
	/**
	 * INPUT: USER NAME
	 * OUTPUT: VOID
	 * FUNCTION: ADDS A PERSON WITH SPECIFIED NAME.
	 */
	public void addPerson(String userName) {
		String newID = randID.getID(32);
		boolean uniqueID = false;
		//creates person with unique ID
		while(!uniqueID) {
			if(findPeople(userName, newID).size() == 0) {
				uniqueID = false; // should this be true?
				break;
			} else {
				newID = randID.getID(32);
			}
		}
		insertPerson(userName, newID);
	}

	/**
	 * INPUT: LOCATION NAME, LAT COORD, LONG COORD
	 * OUTPUT: int
	 * FUNCTION: CHECKS IF GIVEN LOCATION EXISTS IN DATABASE. IF YES: SET AS TRIP LOCATION. ELSE: ADD A LOCATION, SET AS TRIP LOCATION
	 * 0: successful add
	 * 1: failed add
	 * 2: location already exists in database
	 */

	public int setTripLocation(String name, double latitude, double longitude) {
		Location location = null;
		if(findLocation(name, latitude, longitude) == null) {
			location = new Location(name, latitude, longitude);
			insertLocation(location);
		}
		updateTrip(null, tempTrip.getTripID(), location);
		return 0;
	}

	/**
	 * INPUT: VOID
	 * OUTPUT: LIST OF PEOPLE
	 * FUNCTION: RETRIEVE ALL CURRENT USERS IN DATABASE
	 */
	public ArrayList<People> getPeople() {
		return findPeople(null, null); //performs SELECT * FROM People
	}

	/**
	 * INPUT: USER NAME
	 * OUTPUT: LIST OF PEOPLE
	 * FUNCTION: RETURN A LIST NAMES WITH THEIR ASSOCIATED IDS BASED OFF OF THE USER NAME INPUT
	 */
	public ArrayList<People> findUsers(String userName, String userID) {
		return findPeople(userName, userID); //performs SELECT * FROM People WHERE Name = userName
	}

	/**
	 * INPUT: TRIP NAME, TRIP ID
	 * OUTPUT: BOOLEAN
	 * FUNCTION: CREATES TRIP WITH TRIP NAME, GENERATES TRIP ID
	 * RETURN TRUE IF THIS FUNCTION WAS DONE SUCCESSFULLY, FALSE OTHERWISE
	 */
	public boolean setTripName(String tripName) {
		return updateTrip(tripName, tempTrip.getTripID(), null);
	}

	/**
	 * INPUT: TRIP NAME
	 * OUTPUT: LIST OF TRIPS
	 * FUNCTION: RETURN A LIST NAMES WITH THEIR ASSOCIATED IDS BASED OFF OF THE TRIP NAME INPUT
	 */
	public ArrayList<Trip> findTrips(String tripName) {
		ArrayList<Trip> trips = null;
		try {
			String query = "SELECT * FROM Trip WHERE name = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, tripName);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				Trip trip = new Trip(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getDouble(5), rs.getDouble(6));
				trips.add(trip);
			}
			rs.close();
			ps.close();
			return trips;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return trips;
		}
	}

	/**
	 * INPUT: LIST OF ATTRIBUTES
	 * OUTPUT: FILTERED STRING TABLE (LIST OF LIST OF STRING)
	 * FUNCTION: RETURN DATA THAT IS FILTERED THROUGH PROJECTION BASED OFF GIVEN ATTRIBUTES
	 */
	public ArrayList<ArrayList<String>> filterTripData(ArrayList<String> attributes) {
		ArrayList<ArrayList<String>> attrTable = new ArrayList<>();
		String from = "FROM Trip t, People p, TripEvent e, GoOn g " +
				"WHERE t.TripID = g.TripID AND e.TripID = t.TripID AND p.UserID = g.UserID";
		String select = "SELECT";
		if(attributes == null || attributes.size() == 0) { //no attributes
			return attrTable;
		} else{
			for (int i = 0; i < attributes.size(); i++) {
				if (i == 0) {
					select += " " + attributes.get(i); //no comma case
				} else{
					select += ", " + attributes.get(i) + " ";
				}
			}
		}
		String query = select + from;
		try {
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) { //iterates over tuples
				ArrayList<String> temp = new ArrayList<>();
				for (int i = 1; i <= attributes.size(); i++) { //iterates over attributes
					temp.add(rs.getString(i));
					System.out.println(rs.getString(i));
				}
				attrTable.add(temp);
			}
			rs.close();
			ps.close();
			return attrTable;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return attrTable;
		}
	}

	/**
	 * INPUT: WEATHER, TERRAIN, RULES, HAZARDS
	 * OUTPUT: CONDITION ID
	 * FUNCTION: GENERATE A UNIQUE CONDITION ID
	 */
	public String generateConditionID(String weather, String terrain, String rules, String hazards) {
		String conditionID = weather + terrain + rules + hazards;
		if(conditionID.length() > 48) {
			conditionID = conditionID.substring(0, 48);
		} else if(conditionID.length() < 48) {
			conditionID = conditionID + randID.getID(48 - conditionID.length());
		}
		return conditionID;
	}

	/**
	 * INPUT: ACTIVITY
	 * OUTPUT: BOOLEAN
	 * FUNCTION: ADD AN ACTIVITY THAT WILL BE DONE FOR THE TRIP
	 */
	public boolean addActivity(String activity) {
		try {
			String query = "INSERT INTO TripEvent VALUES(?, ?, ?, ?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(
					connection.prepareStatement(query), query, false);
			ps.setString(1, tempTrip.getTripID());
			ps.setString(2, activity);
			ps.setString(3, tempTrip.getOrganizerID());
			ps.setString(4, null);
			ps.setDouble(5, tempTrip.getLatitude());
			ps.setDouble(6, tempTrip.getLongitude());

			ps.executeUpdate();
			connection.commit();
			ps.close();
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			return false;
		}
	}

	/**
	 * INPUT: USER ID
	 * OUTPUT: BOOLEAN
	 * FUNCTION: ADD A CURRENTLY REGISTERED PERSON TO TRIP
	 * RETURN TRUE IF THIS PERSON EXISTS
	 * OTHERWISE RETURN FALSE IF THIS CANNOT BE DONE
	 */

	public boolean addOldPerson(String userID) {
		ArrayList<People> people = findPeople(null, userID);
		if(people == null) {
			return false;
		} else {
			People person = people.get(0);
			goOnTrip(person);
			return true;
		}
	}

	/**
	 * INPUT: TRIP ID
	 * OUTPUT: BOOLEAN
	 * REMOVE A TRIP FROM THE DATABASE USING TRIP ID
	 * RETURN TRUE IF SUCCESS, FALSE OTHERWISE
	 */
	public boolean removeTrip(String tripID) {
		deleteTrip(tripID);
		return true;
	}

	/**
	 * INPUT: VOID
	 * OUTPUT: LIST OF PEOPLE NAMES
	 * FUNCTION: Find locations that were visited more than once
	 *
	 */
	public ArrayList<String> aggregationByHaving() {
		ArrayList<String> locations = new ArrayList<>();
		try {
			String query = "SELECT LOCATIONNAME FROM TRIP GROUP BY LOCATIONNAME HAVING Count(tripID) > 1";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				String name = rs.getString(1);
				locations.add(name);
			}
			ps.close();
			return locations;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			return locations;
		}
	}

	/**
	 * OUTPUT: Number of People and Trip Name in format  Num   Name (ex. 4   PizzaParty!)
	 * Number of people on each trip (Calls aggregationByGrouping)
	 */
	public ArrayList<String> aggregationByGrouping() {
		ArrayList<String> tripCounts = new ArrayList<>();
		try {
			String query = "SELECT Count(*), t.name FROM Trip t, GoOn g, People p WHERE T.TRIPID = g.TRIPID AND g.USERID = p.USERID GROUP BY t.name";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				String tripCount = rs.getString(1) + "   " + rs.getString(2);
				tripCounts.add(tripCount);
			}
			ps.close();
			return tripCounts;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			return tripCounts;
		}
	}

	/**
	 * INPUT: VOID
	 * OUTPUT: Number of Trips and Location Name in same format as aggregationByGrouping()
	 * FUNCTION: Number of trips at location with people who went on another trip
	 *
	 */
	public ArrayList<String> nestedAggregationByGrouping() {
		ArrayList<String> tripCounts = new ArrayList<>();
		try {
			String query = "SELECT t1.LOCATIONNAME, Count(*) FROM Trip t1, GoOn g, People p\n" +
					"                WHERE t1.TRIPID = g.TRIPID AND g.USERID = p.USERID\n" +
					"                 AND p.UserID IN(SELECT p2.UserID\n" +
					"                                 FROM Trip t2, GoOn g2, People p2\n" +
					"                                 WHERE t2.TRIPID = g2.TRIPID AND g2.USERID = p2.USERID\n" +
					"                                 AND t2.tripID <> t1.tripID)\n" +
					"                GROUP BY locationName";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				String tripCount = rs.getString(1) + "   " + rs.getString(2);
				tripCounts.add(tripCount);
			}
			ps.close();
			return tripCounts;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			return tripCounts;
		}
	}

	/**
	 * INPUT: VOID
	 * OUTPUT: LIST OF USER NAMES
	 * FUNCTION: FIND PEOPLE WHO HAVE BEEN ON EVERY SINGLE TRIP
	 */
	public ArrayList<String> divide() {
		ArrayList<String> people = new ArrayList<>();
		try {
			String query = "SELECT DISTINCT p.Name, p.userID FROM People p, GoOn g0 WHERE p.USERID = g0.USERID AND" +
					" NOT EXISTS(SELECT TripID FROM Trip MINUS" +
					"(SELECT TripID FROM GOON g1" +
					" WHERE g1.UserID = g0.UserID))";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				String mvp = rs.getString(1);
				people.add(mvp);
			}
			ps.close();
			return people;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			return people;
		}
	}

	/**
	 * INPUT: LOCATION NAME, LAT COORD, LONG COORD, TYPE, MODEL, COST
	 * OUTPUT: VOID
	 * FUNCTION: CHECKS IF GIVEN LOCATION EXISTS IN DATABASE.
	 * IF YES: SET LOCATION AS LOCATION YOU ARE COMING FROM.
	 * ELSE: ADD A LOCATION, AND SET AS LOCATION YOU ARE COMING FROM.
	 * CREATE A NEW TRANSPORTATION
	 */

	public void setNewTransport(String name, double latitude, double longitude, String type, String model, double cost) {

	}

	/**
	 * INPUT: LOCATION NAME, LAT COORD, LONG COORD, TRANSPORT ID
	 * OUTPUT: BOOLEAN
	 * FUNCTION: CHECKS IF GIVEN LOCATION EXISTS IN DATABASE.
	 * IF YES: SET LOCATION AS LOCATION YOU ARE COMING FROM.
	 * ELSE: ADD A LOCATION, AND SET AS LOCATION YOU ARE COMING FROM.
	 * USE TRANSPORTATION WHICH ALREADY EXISTS IN DATABASE WITH TRANSPORT ID
	 * RETURN TRUE IF THE TRANSPORT EXISTS AND IS VIABLE FOR THE TRIP; ELSE RETURN FALSE
	 */

	public boolean setOldTransport(String name, double latitude, double longitude, String transportID) {
		return false;
	}

	/**
	 * INPUT: CERTIFICATION
	 * OUTPUT: VOID
	 * FUNCTION: CHECK IF CERTIFICATION ALREADY EXISTS
	 * IF YES: ADD TO TRIP
	 * IF NOT: ADD TO DATABASE
	 */
	public void addCertification(String certification) {

	}

	/**
	 * INPUT: SUPPLY, MODEL, COST
	 * OUTPUT: VOID
	 * FUNCTION: ADD NEW SUPPLIES TO DATABASE AND GENERATE NEW SUPPLY ID
	 */
	public void addNewSupplies(String type, String model, double cost) {

	}

	/**
	 * INPUT: SUPPLY ID
	 * OUTPUT: BOOLEAN
	 * FUNCTION: ADD CURRENT SUPPLY IN DATABASE USING SUPPLY ID
	 * RETURN TRUE IF IT IS VIABLE FOR THE TRIP
	 * OTHERWISE RETURN FALSE
	 *
	 */
	public boolean addOldSupplies(String supplyID) {

		return false;
	}



	/**
	 * MIGHT NOT NEED NOW
	 * INPUT: ACTIVITY NAME
	 * OUTPUT: VOID
	 * FUNCTION: RECOMMEND AN ACTIVITY BASED ON THE CONDITION
	 */
	public void reccommendActivity(String activity, String conditionID) {
		// implement
	}

	/**
	 * INPUT: STARTDATE, ENDDATE
	 * OUTPUT: VOID
	 * FUNCTION: ADD A START DATE AND END DATE TO THE TRIP
	 */
	public void setTripTime(String startDate, String endDate) {
	}

	/**
	 * INPUT: ACTIVITY, CERTIFICATION
	 * FUNCTION: ADD REQUIRED CERTIFICATION FOR THE ACTIVITY
	 * CERTIFICATION MAY OR MAY NOT ALREADY EXIST IN DATABASE
	 * RETURN FALSE IF ACTIVITY DOESN'T EXIST
	 */
	public boolean addActivityCertification(String activity, String certification) {
		return false;
	}

	/**
	 * INPUT: ACTIVITY, SUPPLY, MODEL, COST
	 * FUNCTION: ADD NEW SUPPLY TO ACTIVITY
	 * SUPPLY DOESN'T EXIST IN DATABASE BEFORE
	 * RETURN FALSE IF ACTIVITY DOESN'T EXIST IN DATABASE
	 */
	public boolean addActivityNewSupply(String activity, String supply, String model, Double cost) {
		return false;
	}

	/**
	 * INPUT: ACTIVITY, SUPPLY ID
	 * OUTPUT: BOOLEAN
	 * FUNCTION: RETURN TRUE IF SUPPLY CAN BE ADDED TO ACTIVITY AND ACTIVITY EXISTS
	 * ELSE RETURN FALSE
	 */

	public boolean addActivityOldSupply(String activity, String supplyID) {
		return false;
	}

	/**
	 * TODO
	 * INPUT: TRIP ID
	 * OUTPUT: BOOLEAN
	 * FUNCTION: RETURN TRUE IF TRIP NAME CAN BE UPDATED AND UPDATE
	 * RETURN FALSE OTHERWISE
	 */
	public boolean updateTripName(String newName, String tripID) {
		Trip updateTgt = findTrip(tripID);
		return updateTrip(newName, updateTgt.getTripID(), findLocation(updateTgt.getLocationName(), updateTgt.getLatitude(), updateTgt.getLongitude()));
	}


}
