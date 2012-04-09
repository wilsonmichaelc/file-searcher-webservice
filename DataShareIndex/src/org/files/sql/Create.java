package org.files.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * 
 * @author Michael Wilson <wilsonmc@u.washington.edu>
 * @version Feb 2, 2012
 */
public class Create {

	private static Logger log = Logger.getLogger(Create.class);
	private static final String QUERY = "CREATE TABLE IF NOT EXISTS files_tmp ( id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, path VARCHAR(2000), name VARCHAR(500));";

	public void create() throws Throwable {

		Connection connect = null;
		PreparedStatement statement = null;

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

			// Execute the query
			statement.executeUpdate();


		} catch (Throwable t) {
			
			log.error("Could not execute the query: " + QUERY, t);
			throw t;
			
		} finally {
			try {

				if (statement != null) try {statement.close();} catch (SQLException e) { ; }
				statement = null;

				if (connect != null) try {connect.close();} catch (SQLException e) { ; }
				connect = null;
				

			} catch (Throwable t) {

				log.warn("Database connections were not closed.", t);

			}
		}
	}
}