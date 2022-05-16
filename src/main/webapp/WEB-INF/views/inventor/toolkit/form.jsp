<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${readonly}">
	<acme:input-textbox code="inventor.toolkit.code" path="code"/>
	<acme:input-textbox code="inventor.toolkit.name" path="title"/>
	<acme:input-textbox code="inventor.toolkit.description" path="description"/>
	<acme:input-money code="inventor.toolkit.price" path="price"/>
	<acme:input-textarea code="inventor.toolkit.info" path="info"/>
	
	
	<acme:button code="inventor.toolkit.tools" action="/inventor/item/list-tool-toolkit?masterId=${id}"/>
	<acme:button code="inventor.toolkit.components" action="/inventor/item/list-component-toolkit?masterId=${id}"/>
</acme:form>