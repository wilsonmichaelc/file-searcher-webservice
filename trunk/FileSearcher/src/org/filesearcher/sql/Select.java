package org.filesearcher.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.filesearcher.search.Constants;

/**
 * This class handles the selection of a BLAST Request from the database
 * 
 * @author Michael Wilson <wilsonmc@u.washington.edu>
 * @version Feb 2, 2012
 */
public class Select {

	private static Logger log = Logger.getLogger(Select.class);
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private List<String> files = null;
	private String QUERY = "SELECT path FROM files WHERE";

	/**
	 * @param blastRequestId
	 * @return
	 * @throws Throwable
	 */
	public List<String> getResults(String fileName, String folderName, String caseSens) throws Throwable {

	
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DBConnectionManager.getConnection();

			boolean folder = false;
			boolean file = false;
			
			if((folderName == null || folderName == "") && (fileName == null || fileName == "")){
				
				return new ArrayList<String>();
				
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
			QUERY += " LIMIT " + Constants.LIMIT;
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

			// Create new list object
			files = new ArrayList<String>();
			
			// set the request object properties.
			while (resultSet.next()) {
				
				files.add(resultSet.getString("path"));

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
		return files;
	}
}