

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>


<%@page language="java"
	import="acme.entities.patronage.Patronage.Status"%>

<acme:form readonly="true">

	<acme:input-textbox code="inventor.patronage.code" path="code"
		readonly="true" />
	<acme:input-textbox code="inventor.patronage.status" path="status" />
	<acme:input-textbox code="inventor.patronage.creationDate"
		path="creationDate" />
	<acme:input-textbox code="inventor.patronage.endDate" path="endDate" />

	<acme:input-textarea code="inventor.patronage.legal" path="legal" />
	<acme:input-textarea code="inventor.patronage.budget" path="budget" />
	<acme:input-textarea code="inventor.patronage.info" path="info" />


	<acme:button code="inventor.patronage.sponsor"
		action="/any/user-account/show?id=${sponsorId}" />

	<jstl:choose>
		<jstl:when test="${status == 'ACCEPTED'}">
			<acme:button code="inventor.patronage.reports"
				action="/inventor/patronage-report/list?patronageId=${id}" />
			<acme:button code="inventor.patronage.reports.create"
				action="/inventor/patronage-report/create?patronageId=${id}" />
		</jstl:when>
		<jstl:when test="${status == 'PROPOSED'}">
			<acme:submit code="inventor.patronage.accept"
				action="/inventor/patronage/modify?id=${id}&state=patronage.status.accepted" />
			<acme:submit code="inventor.patronage.deny"
				action="/inventor/patronage/modify?id=${id}&state=patronage.status.denied" />
		</jstl:when>
	</jstl:choose>

</acme:form>

