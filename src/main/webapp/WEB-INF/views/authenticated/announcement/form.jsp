<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:input-textbox code="authenticated.announcement.title" path="title"/>
	<acme:input-textbox code="authenticated.announcement.moment" path="creationDate"/>
	<acme:input-textbox code="authenticated.announcement.body" path="body"/>
	<acme:input-textbox code="authenticated.announcement.critical" path="critical"/>
	<acme:input-textbox code="authenticated.announcement.link" path="link" placeholder="default.placeholder.url"/>	
</acme:form>
