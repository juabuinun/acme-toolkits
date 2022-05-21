

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<jstl:set var="sponseeId" value="${sponseeId}" />
<acme:form readonly="${draftMode==false}">

	<acme:input-textbox code="patron.patronage.code" path="code"
		placeholder="patron.patronage.code.placeholder" readonly="${command !='create'}"/>

	<acme:input-textbox code="patron.patronage.status" path="status"
		readonly="true" />

	<acme:input-textbox code="patron.patronage.creationDate"
		path="creationDate" readonly="true"
		placeholder="default.placeholder.moment" />

	<acme:input-textbox code="patron.patronage.endDate" path="endDate"
		placeholder="default.placeholder.moment" />
	<input type="hidden" id="sponseeId" name="sponseeId"
		value="${sponseeId}" />
	<acme:input-textarea code="patron.patronage.legal" path="legal" />
	<acme:input-money code="patron.patronage.budget" path="budget" />
	<acme:input-textbox code="patron.patronage.info" path="info"
		placeholder="default.placeholder.url" />

	<acme:button code="patron.patronage.sponsee"
		action="/any/user-account/show?id=${sponseeId}" />
	<jstl:choose>
		<jstl:when test="${status== 'ACCEPTED'}">
			<acme:button code="patron.patronage.reports"
				action="/patron/patronage-report/list?patronageId=${id}" />
		</jstl:when>
		<jstl:when test="${draftMode==true && command == 'create'}">
			<acme:submit code="patron.patronage.create"
				action="/patron/patronage/create" />
		</jstl:when>
		<jstl:when
			test="${draftMode==true && (command=='show' || command == 'update' || command=='delete' || command=='publish')}">
			<acme:submit code="patron.patronage.publish"
				action="/patron/patronage/publish" />
			<acme:submit code="patron.patronage.update"
				action="/patron/patronage/update" />
			<acme:submit code="patron.patronage.delete"
				action="/patron/patronage/delete" />
		</jstl:when>

	</jstl:choose>


</acme:form>

