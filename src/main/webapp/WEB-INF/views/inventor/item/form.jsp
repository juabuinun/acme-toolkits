<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<%@page language="java" import="acme.entities.item.Item.Type"%>

<acme:form readonly="${draftMode == false}">
	<acme:input-textbox code="inventor.item.form.type" path="itemType"
		readonly="true" />
	<acme:input-textbox code="inventor.item.form.code" path="code"
		placeholder="code.placeholder"
		readonly="${command !='create'}"/>
	<acme:input-textbox code="inventor.item.form.name" path="name" />
	<acme:input-textbox code="inventor.item.form.tech" path="technology" />
	<acme:input-textbox code="inventor.item.form.description"
		path="description" />
	<acme:input-money code="inventor.item.form.price" path="price" />
	<acme:input-textarea code="inventor.item.form.info" path="info" />

	<jstl:if test="${command == 'show' && draftMode == false }">
		<acme:button code="any.item.toolkit"
			action="/any/toolkit/list-item?itemId=${id}" />
	</jstl:if>
	<jstl:choose>
		<jstl:when test="${itemType == 'TOOL'}">
			<jstl:choose>
				<jstl:when
					test="${draftMode == true && (command=='show' || command=='update-tool' || command == 'delete' || command == 'publish-tool')}">
					<acme:submit code="inventor.item.form.update"
						action="/inventor/item/update-tool" />
					<acme:submit code="inventor.item.form.publish"
						action="/inventor/item/publish-tool" />
					<acme:submit code="inventor.item.form.delete"
						action="/inventor/item/delete" />
				</jstl:when>
				<jstl:when test="${draftMode == true && command=='create-tool'}">
					<acme:submit code="inventor.item.form.create"
						action="/inventor/item/create-tool" />
				</jstl:when>
			</jstl:choose>
		</jstl:when>
		<jstl:when test="${itemType == 'COMPONENT'}">
			<jstl:choose>
				<jstl:when
					test="${draftMode == true && (command=='show' || command=='update-component' || command == 'delete' || command == 'publish-component')}">
					<acme:submit code="inventor.item.form.update"
						action="/inventor/item/update-component" />
					<acme:submit code="inventor.item.form.publish"
						action="/inventor/item/publish-component" />
					<acme:submit code="inventor.item.form.delete"
						action="/inventor/item/delete" />
				</jstl:when>
				<jstl:when
					test="${draftMode == true && command=='create-component'}">
					<acme:submit code="inventor.item.form.create"
						action="/inventor/item/create-component" />
				</jstl:when>
			</jstl:choose>
		</jstl:when>
	</jstl:choose>

</acme:form>