package org.files.index;

import java.io.File;

import org.apache.log4j.Logger;
import org.files.sql.AddIndex;
import org.files.sql.Create;
import org.files.sql.DBConnectionManager;
import org.files.sql.Drop;
import org.files.sql.Rename;

public class Main {

	private static Logger log = Logger.getLogger(Main.class);
	private static final String PATH = "/Volumes/Axolotl/";

	public static void main(String[] args) throws Throwable{

		Create create = null;
		Traverse traverse = null;
		AddIndex addIndex = null;
		Drop drop = null;
		Rename rename = null;

		try {
			// Some thing we need
			create = new Create();
			traverse = new Traverse();
			addIndex = new AddIndex();
			drop = new Drop();
			rename = new Rename();

			// Create table files_tmp if not exists
			create.create();
			// Traverse directory
			traverse.traverse(new File(PATH));
			// Add Index to files_tmp
			addIndex.index();
			// Drop table files
			drop.drop();
			// Rename files_tmp to files
			rename.rename();

		}catch(Throwable t){

			log.error("General Error.", t);
			throw t;

		} finally {

			DBConnectionManager.closeConnection();

		}

	}

}
