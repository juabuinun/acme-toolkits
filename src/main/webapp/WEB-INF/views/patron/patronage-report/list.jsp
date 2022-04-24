<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="patron.patronage.report.sequence"
		path="sequenceNumber" width="70%" />
	<acme:list-column code="patron.patronage.report.date"
		path="creationDate" width="30%" />
</acme:list>
