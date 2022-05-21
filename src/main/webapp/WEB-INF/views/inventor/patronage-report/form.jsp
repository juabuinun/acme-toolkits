<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${command != 'create'}">
	<acme:hidden-data path="patronageId" />
	<acme:input-textbox code="inventor.patronage.report.sequence"
		path="sequenceNumber" readonly="true" />
	<acme:input-textbox code="inventor.patronage.report.date"
		path="creationDate" readonly="true" />
	<acme:input-textbox code="inventor.patronage.report.memorandum"
		path="memorandum" />
	<acme:input-textbox code="inventor.patronage.report.info" path="info" />

	<jstl:if test="${command == 'create' }">
		<acme:submit code="inventor.patronage.report.create"
			action="/inventor/patronage-report/create" />
	</jstl:if>
</acme:form>