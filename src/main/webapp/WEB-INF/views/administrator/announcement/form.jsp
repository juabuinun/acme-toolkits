<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="admin.announcement.title" path="title" />
	<acme:input-textbox code="admin.announcement.body" path="body" />
	<acme:input-checkbox code="admin.announcement.critical" path="critical" />
	<acme:input-textbox code="admin.announcement.link" path="link"
		placeholder="default.placeholder.url" />

	<jstl:if test="${command == 'create'}">
		<acme:input-checkbox code="admin.announcement.confirm"
			path="confirmation" />
		<acme:submit code="admin.announcement.create"
			action="/administrator/announcement/create" />
	</jstl:if>
</acme:form>
