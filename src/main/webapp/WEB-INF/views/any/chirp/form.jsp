<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="any.chirp.list.title" path="title" />
	<acme:input-textbox code="any.chirp.list.author" path="author" />
	<acme:input-textbox code="any.chirp.list.body" path="body" />
	<acme:input-textbox code="any.chirp.list.email" path="email" placeholder="default.placeholder.email"/>

	<acme:submit code="any.chirp.create" action="/any/chirp/create" />
</acme:form>