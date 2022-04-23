<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${readonly}">
	<acme:input-textbox code="inventor.item.form.code" path="code"/>
	<acme:input-textbox code="inventor.item.form.name" path="name"/>
	<acme:input-textbox code="inventor.item.form.tech" path="technology"/>
	<acme:input-textbox code="inventor.item.form.description" path="description"/>
	<acme:input-money code="inventor.item.form.price" path="price"/>
	<acme:input-textarea code="inventor.item.form.info" path="info"/>
</acme:form>