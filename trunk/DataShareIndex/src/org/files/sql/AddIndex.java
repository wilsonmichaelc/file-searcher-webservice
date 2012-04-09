package org.files.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * @author Michael Wilson <wilsonmc@u.washington.edu>
 */
public class AddIndex {

	private static Logger log = Logger.getLogger(AddIndex.class);
	private static final String QUERY = "ALTER TABLE files_tmp ADD INDEX(name(10));";


	public void index() throws Throwable {

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