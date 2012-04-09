package org.files.sql;

import java.sql.Connection;
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
public class Rename {

	private static Logger log = Logger.getLogger(Rename.class);
	private static final String QUERY = "RENAME TABLE files_tmp TO files;";

	/**
	 * @param filename
	 * @return 
	 * @return void
	 * @throws Throwable
	 */
	public void rename() throws Throwable {

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
