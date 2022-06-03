<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<form>
	<div class="form-group">
		<label><acme:message code="patron.dashboard.accepted" /></label>
		<div>
			<label> <acme:message code="patron.dashboard.num" />
			</label> <input value="<acme:print value="${chimpumRatio}"/>" type="text"
				class="form-control" readonly />
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="patron.dashboard.min" /></label>
			</div>
			<jstl:forEach items="${chimpumMinBudget}" var="var">
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
			<jstl:forEach items="${chimpumMaxBudget}" var="var">
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
			<jstl:forEach items="${chimpumAvgBudget}" var="var">
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
			<jstl:forEach items="${chimpumStdevBudget}" var="var">
				<div class="row">
					<input value="<acme:print value="${var}"/>" type="text"
						class="form-control" readonly />
				</div>
			</jstl:forEach>
		</div>
	</div>
</form>