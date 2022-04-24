<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="true">
<acme:input-textbox code="inventor.patronage.report.sequence" path="sequenceNumber"/>
	<acme:input-textbox code="inventor.patronage.report.date" path="creationDate"/>
	<acme:input-textbox code="inventor.patronage.report.memorandum" path="memorandum"/>
	<acme:input-textbox code="inventor.patronage.report.info" path="info"/>
</acme:form>