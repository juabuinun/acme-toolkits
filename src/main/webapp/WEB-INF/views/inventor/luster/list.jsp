<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="luster.date.creation" path="creationDate" width="10%" />
	<acme:list-column code="luster.code" path="code" width="10%" />
	<acme:list-column code="luster.title" path="title" width="20%" />
	<acme:list-column code="luster.duration" path="duration" width="20%" />
	<acme:list-column code="luster.budget" path="budget" width="20%" />

</acme:list>

