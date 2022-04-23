
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>


<jstl:if test="${displayProfessional == null}">
	<jstl:set var="displayProfessional" value="false" />
</jstl:if>

<acme:form>
	<acme:input-textbox code="account.name"
		path="identity.name" readonly="true" />
	<acme:input-textbox code="account.surname"
		path="identity.surname" readonly="true" />
	<acme:input-email code="account.email"
		path="identity.email" readonly="true" />

	<jstl:if test="${displayProfessional}">
		<acme:input-textbox code="account.company"
			path="professionalProfile.company" readonly="true" />
		<acme:input-textbox code="account.statement"
			path="professionalProfile.statement" readonly="true" />
		<acme:input-textbox code="account.info"
			path="professionalProfile.info" readonly="true" />
	</jstl:if>
</acme:form>
