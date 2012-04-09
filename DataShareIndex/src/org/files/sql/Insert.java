package org.files.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * This class handles the insetion of BLAST Requests.
 * A request is generated when a user submits the request form on yeastrc.org/pdr/blast.jsp
 * 
 * @author Michael Wilson <wilsonmc@u.washington.edu>
 * @version Feb 2, 2012
 */
public class Insert {

	private static Logger log = Logger.getLogger(Insert.class);
	private static final String QUERY = "INSERT IGNORE INTO files_tmp (id, path, name) VALUES (NULL, ?, ?);";

	/**
	 * @param filename
	 * @return 
	 * @return void
	 * @throws Throwable
	 */
	public void insert(String path, String name) throws Throwable {

		Connection connect = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		ResultSet rskey = null;

		try {

			//Setup the db connection
			connect = DBConnectionManager.getConnection();

		} catch (Throwable t) {

			log.error("Could not get connection from DBConnection Manager", t);
			throw t;

		}

		try {

			// Prepare the statement
			statement = connect.prepareStatement(QUERY);

			// Set the statement parameters
			statement.setString(1, path);
			statement.setString(2, name);


			// Execute the query
			statement.executeUpdate();


		} catch (Throwable t) {
			
			log.error("Could not execute the query: " + QUERY, t);
			throw t;
			
		} finally {
			try {
				
				// Close the connections.
				if (result != null) try {result.close();} catch (SQLException e) { ; }
				result = null;

				if (statement != null) try {statement.close();} catch (SQLException e) { ; }
				statement = null;

				if (connect != null) try {connect.close();} catch (SQLException e) { ; }
				connect = null;
				
				if (rskey != null) try {rskey.close();} catch (SQLException e) { ; }
				rskey = null;

			} catch (Throwable t) {

				log.warn("Database connections were not closed.", t);

			}
		}
	}
}
