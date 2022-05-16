<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="inventor.item.form.code" path="code"/>
	<acme:input-textbox code="inventor.item.form.name" path="name"/>
	<acme:input-textbox code="inventor.item.form.tech" path="technology"/>
	<acme:input-textbox code="inventor.item.form.description" path="description"/>
	<acme:input-money code="inventor.item.form.price" path="price"/>
	<acme:input-textarea code="inventor.item.form.info" path="info"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish') && draftMode == true}">
			<acme:submit code="inventor.item.form.update" action="/inventor/item/update"/>
			<acme:submit code="inventor.item.form.delete" action="/inventor/item/delete"/>
			<acme:submit code="inventor.item.form.publish" action="/inventor/item/publish"/>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
		<acme:submit code="inventor.item.form.create" action="/inventor/item/publish"/>
		</jstl:when>
	</jstl:choose>
</acme:form>