<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${readonly}">
	<acme:input-textbox code="any.toolkit.code" path="code"/>
	<acme:input-textbox code="any.toolkit.name" path="title"/>
	<acme:input-textbox code="any.toolkit.description" path="description"/>
	<acme:input-money code="any.toolkit.price" path="price"/>
	<acme:input-textarea code="any.toolkit.info" path="info"/>
	
	<acme:button code="any.toolkit.tools" action="/any/item/list-tool-toolkit?masterId=${id}"/>
	<acme:button code="any.toolkit.components" action="/any/item/list-component-toolkit?masterId=${id}"/>
</acme:form>