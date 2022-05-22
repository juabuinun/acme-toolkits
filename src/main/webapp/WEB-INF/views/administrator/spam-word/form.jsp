<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${command != 'create'}">
	<acme:input-textbox code="spam.word" path="word" />
	<acme:input-checkbox code="spam.strong" path="strong" />
	<acme:input-textbox code="spam.language" path="language" />

	<jstl:choose>
		<jstl:when test="${command == 'create'}">
			<acme:submit code="spam.create"
				action="/administrator/spam-word/create" />
		</jstl:when>
		<jstl:when test="${command == 'show'}">
			<acme:submit code="spam.delete"
				action="/administrator/spam-word/delete" />
		</jstl:when>
	</jstl:choose>
</acme:form>