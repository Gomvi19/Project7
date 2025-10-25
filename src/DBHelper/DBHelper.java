//DBHelper class created by db helper, creates the connection to the database created in SQL studio as well as the execute commands that modify the table
package DBHelper;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class DBHelper {
	private final String DATABASE_NAME = "C:\\sqlite\\newUniversity.db";
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	public DBHelper() {
		connection = null;
		statement = null;
		resultSet = null;
	}

    /**
     * Establishes a connection to the SQLite database
     * Purpose:
     *  Loads the SQLite JDBC driver
     *  Creates a database connection and initializes a Statement object for executing SQL commands
     * Arguments:
     *  None
     * Return Value:
     *  None
     *Implementation Details:
     *  The first try-catch block loads the JDBC driver using Class.forName()
     *  If the driver class is not found, a ClassNotFoundException is caught and printed
     *  The second try-catch block creates a database connection using the database name,
     *  and initializes a Statement object from that connection
     *  If the connection or statement fails, a SQLException is caught and printed
     */
	private void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * Closes all open database resources.
     *  Purpose: properly releases database related resources to prevent memory leaks
     *
     * Arguments: None
     *
     * Return Value: None
     *
     * Implementation Details:
     *  Attempts to close the database connection, statement, and resultSet if they are open
     *  A conditional check (if resultSet != null) ensures that the ResultSet is only closed if it exists
     *  Exceptions during closure are caught and printed
     */
	private void close() {
		try {
			connection.close();
			statement.close();
			if (resultSet != null)
			    resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * Converts an ArrayList of ArrayLists into a 2D Object array
     *
     * Purpose: transforms an ArrayList of ArrayLists into a static two-dimensional array
     * Arguments: an ArrayList of ArrayLists, where each inner list represents a row of data
     *
     * Return Value: returns a two dimensional Object array containing the same data as the input list
     *
     * Implementation Details:
     * - A for loop iterates over each row in the list
     * - Each inner ArrayList (representing a row) is converted to an Object array using toArray()
     * - The resulting 2D Object array is returned
     */
	private Object[][] arrayListTo2DArray(ArrayList<ArrayList<Object>> list) {
		Object[][] array = new Object[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			ArrayList<Object> row = list.get(i);
			array[i] = row.toArray(new Object[row.size()]);
		}
		return array;
	}
    /**
     * Executes a SQL command that does not return a result set
     *
     * Purpose: runs SQL statements that modify the database without returning query results
     *
     * Arguments: a String containing the SQL statement to be executed
     *
     * Return Value: none
     *
     * Implementation Details:
     * - Calls connect() to establish a connection to the database
     * - Executes the SQL statement using the Statement object's execute() method
     * - Uses a try catch finally block to ensure that database resources are closed
     *   even if an exception occurs
     */
	protected void execute(String sql) {
		try {
			connect();
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}
    /**
     * Executes a SQL SELECT query and returns the results as a DefaultTableModel.
     *
     * Purpose: runs a SQL query, retrieves its results, and converts them into a table model
     *
     * Arguments: a String containing the SQL SELECT statement to be executed
     *
     * Return Value: a DefaultTableModel object containing the query results and column names
     *
     * Implementation Details:
     * - Connects to the database and executes the SQL query
     * - Retrieves metadata (column names and count) from the ResultSet
     * - A for loop collects column names into an ArrayList
     * - A while loop iterates over each row of the ResultSet:
     *     - An inner for loop adds each column’s value to a subresult list
     *     - Each subresult (representing one row) is added to the main results list
     * - After fetching all data, the ResultSet and connections are closed
     * - Converts the results into a 2D Object array using arrayListTo2DArray() and creates
     *   a DefaultTableModel with column names
     * - Returns the populated DefaultTableModel
     */
	protected DefaultTableModel executeQueryToTable(String sql) {
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> columns = new ArrayList<Object>();
		connect();
		try {
			resultSet = statement.executeQuery(sql);
			int columnCount = resultSet.getMetaData().getColumnCount();
			for (int i = 1; i <= columnCount; i++)
			    columns.add(resultSet.getMetaData().getColumnName(i));
			while (resultSet.next()) {
				ArrayList<Object> subresult = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++)
				    subresult.add(resultSet.getObject(i));
				    result.add(subresult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return new DefaultTableModel(arrayListTo2DArray(result), columns.toArray());
	}
    /**
     * Executes a SQL SELECT query and returns the results as a list of rows
     *
     * Purpose: runs a SQL query and stores the retrieved data in a flexible ArrayList structure
     *
     * Arguments: a String containing the SQL SELECT statement to be executed
     *
     * Return Value: an ArrayList of ArrayLists, where each inner ArrayList represents a row of data
     *
     * Implementation Details:
     * - Connects to the database and executes the SQL query
     * - Retrieves the number of columns from ResultSet metadata
     * - A while loop iterates over each record in the ResultSet
     *     - An inner for loop extracts each column value and stores it in a subresult list
     *     - Each subresult is then added to the main result list
     * - After processing all records, closes the connection and returns the list of results
     */
	protected ArrayList<ArrayList<Object>> executeQuery(String sql) {
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		connect();
		try {
			resultSet = statement.executeQuery(sql);
			int columnCount = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				ArrayList<Object> subresult = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++) {
					subresult.add(resultSet.getObject(i));
				}
				result.add(subresult);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		close();
		return result;
	}

}