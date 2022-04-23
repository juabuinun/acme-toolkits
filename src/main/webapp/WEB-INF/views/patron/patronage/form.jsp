

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<acme:form>

	<acme:input-textbox code="inventor.patronage.code" path="code"
		readonly="true" />
	<acme:input-textbox code="inventor.patronage.status" path="status" />
	<acme:input-textbox code="inventor.patronage.creationDate"
		path="creationDate" />
	<acme:input-textbox code="inventor.patronage.endDate" path="endDate" />

	<acme:input-textarea code="inventor.patronage.legal" path="legal" />
	<acme:input-textarea code="inventor.patronage.budget" path="budget" />
	<acme:input-textarea code="inventor.patronage.info" path="info" />
	
	<security:authorize access="hasRole('Patron')">
		<acme:button code="inventor.patronage.sponsee"
			action="/any/user-account/show?id=${sponseeId}" />
		<acme:button code="inventor.patronage.reports"
			action="/patron/patronage-report/list?patronageId=${id}" />
	</security:authorize>


</acme:form>

