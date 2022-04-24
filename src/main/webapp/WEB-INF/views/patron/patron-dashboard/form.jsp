<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>


<form>
	<div class="form-group">
		<label><acme:message code="patron.dashboard.accepted" /></label>
		<div>
			<label> <acme:message code="patron.dashboard.num" />
			</label> <input value="<acme:print value="${numAccepted}"/>" type="text"
				class="form-control" readonly />
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.min" /></label>
			</div>
			<jstl:forEach items="${minAccepted}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.max" /></label>
			</div>
			<jstl:forEach items="${maxAccepted}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.avg" /></label>
			</div>
			<jstl:forEach items="${avgAccepted}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.stdev" /></label>
			</div>
			<jstl:forEach items="${stdevAccepted}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
	</div>


	<div class="form-group">
		<label><acme:message code="patron.dashboard.proposed" /></label>
		<div>
			<label> <acme:message code="patron.dashboard.num" />
			</label> <input value="<acme:print value="${numProposed}"/>" type="text"
				class="form-control" readonly />
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.min" /></label>
			</div>
			<jstl:forEach items="${minProposed}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.max" /></label>
			</div>
			<jstl:forEach items="${maxProposed}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.avg" /></label>
			</div>
			<jstl:forEach items="${avgProposed}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.stdev" /></label>
			</div>
			<jstl:forEach items="${stdevProposed}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
	</div>
	
	<div class="form-group">
		<label><acme:message code="patron.dashboard.denied" /></label>
		<div>
			<label> <acme:message code="patron.dashboard.num" />
			</label> <input value="<acme:print value="${numDenied}"/>" type="text"
				class="form-control" readonly />
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.min" /></label>
			</div>
			<jstl:forEach items="${minDenied}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.max" /></label>
			</div>
			<jstl:forEach items="${maxDenied}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.avg" /></label>
			</div>
			<jstl:forEach items="${avgDenied}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.stdev" /></label>
			</div>
			<jstl:forEach items="${stdevDenied}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
	</div>
</form>
