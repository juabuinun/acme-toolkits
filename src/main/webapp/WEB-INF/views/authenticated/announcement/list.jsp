<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.announcement.title" path="title" width="20%"/>
	<acme:list-column code="authenticated.announcement.moment" path="creationDate" width="10%"/>
	<acme:list-column code="authenticated.announcement.body" path="body" width="70%"/>
</acme:list>