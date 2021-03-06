<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="any.item.list.code" path="code" width="20%" />
	<acme:list-column code="any.item.list.name" path="name" width="20%" />
	<acme:list-column code="any.item.list.tech" path="technology"
		width="20%" />
	<acme:list-column code="any.item.list.price" path="price" width="20%" />
	<jstl:if
		test="${command == 'list-component-toolkit' || command == 'list-tool-toolkit'}">
		<acme:list-column code="any.item.list.qty" path="quantity" width="10%" />
	</jstl:if>
</acme:list>

