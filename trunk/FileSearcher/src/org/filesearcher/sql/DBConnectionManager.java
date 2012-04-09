package org.filesearcher.sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionManager {
	
	/**
	 * Get DataSource from JNDI as setup in Application Server and get database connection from it
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws Exception {

		try {
			
			Context ctx = new InitialContext();
			DataSource ds;
			Connection conn;

			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/datashareindex");


			if (ds != null) {
				conn = ds.getConnection();
				if (conn != null) { return conn; }
				else { throw new SQLException("Got a null connection..."); }
			}

			throw new SQLException("Got a null DataSource...");
			
		} catch (NamingException ne) {
			
			throw new SQLException("Naming exception: " + ne.getMessage());
		}
	}

}
