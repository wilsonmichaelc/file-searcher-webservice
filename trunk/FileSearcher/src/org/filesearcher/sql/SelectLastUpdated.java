
package org.filesearcher.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * This class handles the selection of a BLAST Request from the database
 * 
 * @author Michael Wilson <wilsonmc@u.washington.edu>
 * @version Feb 2, 2012
 */
public class SelectLastUpdated {

	private static Logger log = Logger.getLogger(SelectLastUpdated.class);
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String QUERY = "SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = 'datashareindex' AND TABLE_NAME = 'files';";
	private String updateTime;
	
	/**
	 * @param blastRequestId
	 * @return
	 * @throws Throwable
	 */
	public String getResults() throws Throwable {


		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DBConnectionManager.getConnection();

			// Statements allow to issue SQL queries to the database
			preparedStatement = connect.prepareStatement(QUERY);

			// Result set get the result of the SQL query
			resultSet = preparedStatement.executeQuery();

			// set the request object properties.
			while (resultSet.next()) {

				updateTime = resultSet.getString("UPDATE_TIME");

			}

		} catch (Throwable t) {

			log.error("Could not execute query: " + QUERY, t);
			throw t;

		} finally {
			try {

				// Close the connections.
				if (resultSet != null) try {resultSet.close();} catch (SQLException e) { log.warn("Failed to close resultSet.", e); }
				resultSet = null;

				if (preparedStatement != null) try {preparedStatement.close();} catch (SQLException e) { log.warn("Failed to close preparedStatement.", e); }
				preparedStatement = null;

				if (connect != null) try {connect.close();} catch (SQLException e) { log.warn("Failed to close connect.", e); }
				connect = null;

			} catch (Throwable t) {

				log.warn("Database connection(s) were not closed.", t);

			}
		}
		return updateTime;
	}
}