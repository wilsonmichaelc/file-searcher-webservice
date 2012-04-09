<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
response.addHeader("Cache-control", "no-store"); // tell proxy not to cache
response.addHeader("Cache-control", "max-age=0"); // stale right away
%>

<html>
<head>

 <meta name="keywords" content="search, public, data, experimental microscopy">
 <meta name="description" content="The Data Share Search Program was designed to search the shared resources of the DavisLab's shared server space.">
 <link REL="stylesheet" TYPE="text/css" HREF="/Searcher/css/style.css">
</head>

<body>

