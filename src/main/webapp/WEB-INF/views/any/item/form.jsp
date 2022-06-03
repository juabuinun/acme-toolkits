<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>


<acme:form readonly="${readonly}">
	<acme:input-textbox code="any.item.form.code" path="code" />
	<acme:input-textbox code="any.item.form.name" path="name" />
	<acme:input-textbox code="any.item.form.tech" path="technology" />
	<acme:input-textbox code="any.item.form.description" path="description" />
	<acme:input-money code="any.item.form.price" path="price" />
	<acme:input-textarea code="any.item.form.info" path="info" />

	<acme:button code="any.item.toolkit"
		action="/any/toolkit/list-item?itemId=${id}" />



</acme:form>