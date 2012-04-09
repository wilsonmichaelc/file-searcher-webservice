package org.files.index;

import java.io.File;

import org.apache.log4j.Logger;
import org.files.sql.DropTmpFiles;
import org.files.sql.Insert;

public class Traverse {

	private static Logger log = Logger.getLogger(Traverse.class);

	public void insert(File file) throws Throwable{

		Insert i = null;

		try{

			i = new Insert();
			i.insert(file.getAbsolutePath(), file.getName());


		}catch(Throwable t){

			log.error("Failed to insert " + file.getName(), t);
			throw t;
		}

	}

	public void traverse( File file ) throws Throwable
	{

		try{
			if (!file.exists()){
				
				log.error("Directory " + file.toString() + " does not exist.");
				log.error("Table: files_tmp will be removed.");
				log.error("Table: files remains unchanged.");
				DropTmpFiles dtf = new DropTmpFiles();
				dtf.drop();
				throw new Throwable("Directory does not exist!"); 
			}

			// Insert the entry
			if (file.isFile()){
				insert(file);
				//System.out.println( file ) ;
			}

			// Check if it is a directory
			if( file.isDirectory() )
			{
				// Get a list of all the entries in the directory
				String[] entries = file.list() ;

				// Ensure that the list is not null
				if( entries != null )
				{
					// Loop over all the entries
					for( String entry : entries )
					{
						// Recursive call to traverse, but exclude these directories
						if(!(entry.equals("SpecialFolder") ||  
								entry.startsWith("."))){
							traverse( new File(file,entry) ) ;
						}
					}
				}
			}


		}catch(Throwable t){

			log.error("Could not traverse directory." + file.getAbsolutePath() + ".", t);
			throw t;

		}
	}

}
