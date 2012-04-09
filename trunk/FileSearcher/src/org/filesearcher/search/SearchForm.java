package org.filesearcher.search;

import org.apache.struts.action.ActionForm;

/**
 * 
 * @author Michael Wilson <wilsonmc@u.washington.edu>
 * @version Feb 2, 2012
 */
public class SearchForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String fileName = "";
	private String folderName = "";
	private String caseSensitive = "";

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * @param folderName the folderName to set
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * @return the caseSense
	 */
	public String getCaseSensitive() {
		return caseSensitive;
	}


	/**
	 * @param caseSense the caseSense to set
	 */
	public void setCaseSensitive(String caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
	
}
