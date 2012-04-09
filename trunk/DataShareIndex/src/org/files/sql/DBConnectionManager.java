package org.files.sql;

import java.sql.Connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public class DBConnectionManager {

   private static Logger log = Logger.getLogger(DBConnectionManager.class);


   private static BasicDataSource dataSource;


   public static final String DATABASE = "datashareindex";


   //  Not synchronized since single threaded

   public static Connection getConnection( ) throws Exception {

       Connection connection = null;


       try {

           if ( dataSource == null ) {

               Class.forName("com.mysql.jdbc.Driver");

               String user = "datashareindex";
               String password = "DataShareIndex";
               String host = "localhost";
               String port = "3306";

               // Construct DataSource
               dataSource = new BasicDataSource();
               dataSource.setUrl("jdbc:mysql://" + host + ":" + port + "/" + DATABASE +
                         "?autoReconnect=true&jdbcCompliantTruncation=false" );


               dataSource.setUsername( user );
               dataSource.setPassword( password );



               dataSource.setValidationQuery("select 1 from dual");

               dataSource.setTestOnBorrow( true );

               dataSource.setMinEvictableIdleTimeMillis( 21600000 );

               dataSource.setTimeBetweenEvictionRunsMillis( 30000 );


           }


           connection = dataSource.getConnection();


       } catch (Exception ex) {
           log.error( "ImageUploadDBConnectionFactory: getConnection( ): Exception " + ex.toString(), ex );

           throw ex;
       }

       return connection;

   }


   /**
    * Close the datasource
    *
    * @throws Exception
    */
   public static void closeConnection( ) throws Exception {

       if ( dataSource != null ) {
           dataSource.close();

           dataSource = null;
       }
   }


   /**
    * Test the connection to the database
    * @throws Throwable
    */
   public static void testConnection() throws Throwable {

       //  The datasource will test the connection so don't need to test it here

       try {
           Connection connection = getConnection();

           connection.close();

       } catch (Exception ex) {
           log.error( "ImageUploadDBConnectionFactory: testConnection( ): Exception " + ex.toString(), ex );

           throw ex;

       } finally {

           try {
               closeConnection( );

           } catch (Exception ex) {
               log.error( "ImageUploadDBConnectionFactory: closeConnection( ): Exception " + ex.toString(), ex );
           }
       }
   }

}