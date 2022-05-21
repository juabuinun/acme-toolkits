<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:input-textbox code="admin.announcement.title" path="title"/>
	<acme:input-textbox code="admin.announcement.body" path="body"/>
	<acme:input-checkbox code="admin.announcement.critical" path="critical"/>
	<acme:input-textbox code="admin.announcement.link" path="link"/>	
</acme:form>
