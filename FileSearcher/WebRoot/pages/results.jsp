
<%@page import="org.filesearcher.search.Constants"%><%@ taglib
	uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ include file="/includes/imports.inc"%>
<%@ include file="/includes/header.jsp"%>
<center>
	<div style="width: 600px; border: 1px solid black;">
		<html:form action="/search">
			<span style="font-size: 26px;">Search for a file</span><br /><span style="font-style: italic">(Last Indexed: <bean:write name="updated" />)</span>
			<br />
			<%-- Search box --%>
			<html:text size="45" property="fileName"></html:text>
			<br />
			<br />
			<div style="font-style: italic;">
				The '*' character is a wildcard.
				<br />
				If you place the star in your search term like this: '*foo.txt'
				<br />
				The results will contain files that begin with anything followed by
				'foo.txt'.
				<br />
				<br />
				Likewise, if your search term is: 'foo*'
				<br />
				Results will contain any file beginning with 'foo' and ending with
				anything.
				<br />
				<br />
			</div>
			<span style="font-size: 18px;">Folder Name (optional)</span>
			<br />
			<%-- Folder Name Search Box --%>
			<html:text size="20" property="folderName"></html:text>
			<br />
			<br />
			<div style="font-style: italic;">
				If you specify a folder name or partial name, the search will only
				find
				<br />
				files that are also in a folder matching this name.
				<br />
				<br />
			</div>
		Case Sensitive<br />
			<input type="radio" name="caseSensitive" value="no" checked>No
			<input type="radio" name="caseSensitive" value="yes">Yes
		<br />
			<br />
			<html:submit style="font-size: 24px;" value="Search"></html:submit>
		</html:form>
		<br />
		<span style="font-size: 8pt;"> FileSearcher - Version 1.0<BR>
			Created by Michael Wilson </span><br />
	</div>


</center>

<center>
	<div>
		<c:if test="${ not empty ran }">
			<c:choose>
				<c:when test="${ not empty results }">
				<span style="font-size: 24px;">Search Results:</span> 
				<c:if test="${ not empty totalResults }">
						<span style="color: red; font-weight: bold;">(Showing <%=Constants.LIMIT%>
							of <bean:write name="totalResults" />)</span>
					</c:if>
					<br />
					<br />
					<logic:iterate id="result" name="results">

						<bean:write name="result" />

						<br />

					</logic:iterate>
				</c:when>

				<c:otherwise>
				There were no files found.
			</c:otherwise>
			</c:choose>
		</c:if>
	</div>
</center>

<%@ include file="/includes/footer.jsp"%>