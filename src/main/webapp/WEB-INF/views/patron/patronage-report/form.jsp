<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:input-textbox code="patron.patronage.report.sequence"
		path="sequenceNumber" readonly="true" />
	<acme:input-textbox code="patron.patronage.report.date"
		path="creationDate" readonly="true"
		placeholder="default.placeholder.moment" />
	<acme:input-textbox code="patron.patronage.report.memorandum"
		path="memorandum" />
	<acme:input-textbox code="patron.patronage.report.info" path="info" placeholder="default.placeholder.url"/>
</acme:form>