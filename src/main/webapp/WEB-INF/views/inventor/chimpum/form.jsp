<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<%@page language="java" import="acme.entities.item.Item.Type"%>

<jstl:set var="itemId" value="${itemId}" />
<acme:form>
	<acme:hidden-data path="itemId" />
	<acme:input-textbox code="chimpum.code" path="code"
		readonly="${ command != 'create'}" placeholder="code.placeholder" />

	<acme:input-textbox code="chimpum.title" path="title" />
	<acme:input-textarea code="chimpum.description" path="description" />

	<acme:input-textbox code="chimpum.date.creation" path="creationDate"
		readonly="true" placeholder="default.placeholder.moment" />

	<acme:input-textbox code="chimpum.duration" path="duration"
		placeholder="" readonly="true" />
	<acme:input-textbox code="chimpum.date.start" path="startDate"
		placeholder="general.placeholder.date" readonly="${ command != 'create'}" />
	<acme:input-textbox code="chimpum.date.end" path="endDate"
		placeholder="general.placeholder.date" />

	<acme:input-money code="chimpum.budget" path="budget" />

	<acme:input-textarea code="chimpum.info" path="info"
		placeholder="default.placeholder.url" />

	<jstl:choose>
		<jstl:when test="${command == 'create' }">

			<acme:submit code="chimpum.create" action="/inventor/chimpum/create" />
		</jstl:when>
		<jstl:when test="${command != 'create' }">
			<acme:submit code="chimpum.update" action="/inventor/chimpum/update" />
			<acme:submit code="chimpum.delete" action="/inventor/chimpum/delete" />

			<acme:button code="chimpum.item" action="/inventor/item/show?id=${itemId}" />
		</jstl:when>
	</jstl:choose>

</acme:form>

