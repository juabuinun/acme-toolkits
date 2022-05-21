
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<acme:form>
	<acme:input-textbox code="account.name" path="identity.name"
		readonly="true" />
	<acme:input-textbox code="account.surname" path="identity.surname"
		readonly="true" />
	<acme:input-email code="account.email" path="identity.email"
		readonly="true" />

	<jstl:if test="${displayProfessional == true}">
		<acme:input-textbox code="account.company"
			path="account.company" readonly="true" />
		<acme:input-textbox code="account.statement"
			path="account.statement" readonly="true" />
		<acme:input-textbox code="account.info"
			path="account.info" readonly="true" />

		<security:authorize access="hasRole('Patron')">
			<jstl:if test="${inventor == true}">
				<acme:button code="account.patronage.create" action="/patron/patronage/create?sponseeId=${id}"/>
			</jstl:if>
		</security:authorize>
	</jstl:if>
</acme:form>
