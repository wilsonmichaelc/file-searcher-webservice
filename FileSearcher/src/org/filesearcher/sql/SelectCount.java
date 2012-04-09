
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
public class SelectCount {

	private static Logger log = Logger.getLogger(Select.class);
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String QUERY = "SELECT COUNT(path) AS totalResults FROM files WHERE";
	private int totalResults;
	
	/**
	 * @param blastRequestId
	 * @return
	 * @throws Throwable
	 */
	public int getResults(String fileName, String folderName, String caseSens) throws Throwable {


		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DBConnectionManager.getConnection();

			boolean folder = false;
			boolean file = false;

			if((folderName == null || folderName == "") && (fileName == null || fileName == "")){
				
				return 0;
				
			} else {
				
				if(folderName != null && folderName != ""){
					folder = true;
					folderName = folderName.replace("*", "");
					folderName = "%" + folderName + "%";
					QUERY += " path LIKE ?";
				}
				if(fileName != null && fileName != ""){
					file = true;
					if (folder == true){
						QUERY += " AND";
					}
					if(caseSens.equals("yes")){
						QUERY += " BINARY";
					}
					if (fileName.contains("*")){
						fileName = fileName.replace("*", "%");
						QUERY += " name LIKE ?";
					} else {
						QUERY += " name = ?";
					}
				}
				
			}

			// Statements allow to issue SQL queries to the database
			preparedStatement = connect.prepareStatement(QUERY);
			
			if (file == true && folder == true){

				log.debug("Filename: " + fileName + " Foldername: " + folderName);
				preparedStatement.setString(1, folderName);
				preparedStatement.setString(2, fileName);


			}else if (file == true && folder == false){

				log.debug("Filename: " + fileName);
				preparedStatement.setString(1, fileName);

			}else if (file == false && folder == true){

				log.debug("Foldername: " + folderName);
				preparedStatement.setString(1, folderName);

			}

			log.debug(QUERY);


			// Result set get the result of the SQL query
			resultSet = preparedStatement.executeQuery();

			// set the request object properties.
			resultSet.next();

			totalResults = resultSet.getInt(1);


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
		return totalResults;
	}
}