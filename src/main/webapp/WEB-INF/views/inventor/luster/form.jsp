<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<%@page language="java" import="acme.entities.item.Item.Type"%>

<jstl:set var="itemId" value="${itemId}" />
<acme:form>
	<acme:hidden-data path="itemId" />
	<acme:input-textbox code="luster.code" path="code"
		readonly="true" placeholder="code.placeholder" />

	<acme:input-textbox code="luster.title" path="title" />
	<acme:input-textarea code="luster.description" path="description" />

	<acme:input-textbox code="luster.date.creation" path="creationDate"
		readonly="true" placeholder="default.placeholder.moment" />

	<acme:input-textbox code="luster.duration" path="duration"
		placeholder="" readonly="true" />
	<acme:input-textbox code="luster.date.start" path="startDate"
		placeholder="general.placeholder.date" readonly="${ command != 'create'}" />
	<acme:input-textbox code="luster.date.end" path="endDate"
		placeholder="general.placeholder.date" />

	<acme:input-money code="luster.budget" path="budget" />

	<acme:input-textarea code="luster.info" path="info"
		placeholder="default.placeholder.url" />

	<jstl:choose>
		<jstl:when test="${command == 'create' }">

			<acme:submit code="luster.create" action="/inventor/luster/create" />
		</jstl:when>
		<jstl:when test="${command != 'create' }">
			<acme:submit code="luster.update" action="/inventor/luster/update" />
			<acme:submit code="luster.delete" action="/inventor/luster/delete" />

			<acme:button code="luster.item" action="/inventor/item/show?id=${itemId}" />
		</jstl:when>
	</jstl:choose>

</acme:form>

