<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="spam.word" path="word" width="50%"/>
	<acme:list-column code="spam.strong" path="strong" width="25%"/>
	<acme:list-column code="spam.language" path="language" width="25%"/>
</acme:list>

<acme:button code="spam.create" action="/administrator/spam-word/create"/>