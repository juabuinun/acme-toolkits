<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="inventor.patronage.code" path="code" width="20%"/>
	<acme:list-column code="inventor.patronage.status" path="status" width="20%"/>
	<acme:list-column code="inventor.patronage.creationDate" path="creationDate" width="20%"/>
	<acme:list-column code="inventor.patronage.endDate" path="endDate" width="20%"/>
	<acme:list-column code="inventor.patronage.budget" path="budget" width="20%"/>
</acme:list>
