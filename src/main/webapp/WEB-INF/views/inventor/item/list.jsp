<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="inventor.item.list.code" path="code"
		width="10%" />
	<acme:list-column code="inventor.item.list.name" path="name"
		width="20%" />
	<acme:list-column code="inventor.item.list.tech" path="technology"
		width="20%" />
	<acme:list-column code="inventor.item.list.price" path="price"
		width="20%" />
	<acme:list-column code="inventor.item.list.published" path="published"
		width="10%" />
</acme:list>

