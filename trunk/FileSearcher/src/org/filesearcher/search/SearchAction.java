package org.filesearcher.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.filesearcher.sql.Select;
import org.filesearcher.sql.SelectCount;
import org.filesearcher.sql.SelectLastUpdated;


/**
 * 
 * @author Michael Wilson <wilsonmc@u.washington.edu>
 * @version Feb 2, 2012
 */
public class SearchAction extends Action {

	private static final Logger log = Logger.getLogger(SearchAction.class);
	private static final String FIND_FORWARD_ERROR = "error";
	private static final String FIND_FORWARD_SUCCESS = "success";

    /**
     * 
     * @param mapping 
     * @param form
     * @param request
     * @param response
     * @return mapping "success" or "error"
     * @throws Exception
     */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest httpRequest, HttpServletResponse response)  
	{

		SearchForm fm = null;
		String fileName = "";
		String folderName = "";
		String caseSens = "";
		List<String> results = null;
		
		httpRequest.setAttribute("updated", getLastUpdateTime());
		
		try {

			// Get parameters from form
			fm = (SearchForm) form;
			fileName = fm.getFileName();
			folderName = fm.getFolderName();
			caseSens = fm.getCaseSensitive();
			
		} catch (Throwable t) {
			
			log.error("Could not get parameters from the form. ", t);
			return mapping.findForward(FIND_FORWARD_ERROR);
			
		}

		if ( (fileName == null || fileName.equals("") ) &&  (folderName == null || folderName.equals("") ) ){
			
			return mapping.findForward(FIND_FORWARD_SUCCESS);
		
		}
		httpRequest.setAttribute("ran", "yes");
		// Submit the query and get the results in the form of a list
		//Searcher searcher = null;
		Select select = null;
		SelectCount selectCount = null;
		int totalResults = 0;
		try {
			
			//searcher = new Searcher();
			select = new Select();
			selectCount = new SelectCount();
			results = new ArrayList<String>();
			log.debug("Calling Search with filename: " + fileName + " folderName: " + folderName + " case sensitive: " + caseSens);
			totalResults = selectCount.getResults(fileName, folderName, caseSens);
			results = select.getResults(fileName, folderName, caseSens);
			
		} catch (Throwable t){
			
			log.error("Failed to get results.", t);
			return mapping.findForward(FIND_FORWARD_ERROR);
			
		}
		
		if (totalResults > Constants.LIMIT){
			httpRequest.setAttribute("totalResults", totalResults);
		}
		
		httpRequest.setAttribute("results", results);
		return mapping.findForward(FIND_FORWARD_SUCCESS);
	}
	
	
	public String getLastUpdateTime(){
		
		SelectLastUpdated lastUpdate = null;
		String updateTime = "";
		
		try{
			lastUpdate = new SelectLastUpdated();
			updateTime = lastUpdate.getResults();
		} catch (Throwable t){
			log.warn("Could not get last update time.");
			updateTime = "unknown";
		}
		
		return updateTime;
	}
}

