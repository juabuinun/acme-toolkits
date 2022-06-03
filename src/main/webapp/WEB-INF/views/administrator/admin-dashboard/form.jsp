<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<form>
	<div class="form-group">
		<label><acme:message code="admin.dashboard.luster" /></label>
		<div>
			<label> <acme:message code="admin.dashboard.luster.ratio" />
			</label> <input value="<acme:print value="${chimpumRatio}"/>" type="text"
				class="form-control" readonly />
		</div>
		<div class="col">
			<div class="row">
				<label> <acme:message code="admin.dashboard.luster.min" /></label>
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
				<label> <acme:message code="admin.dashboard.luster.max" /></label>
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
				<label> <acme:message code="admin.dashboard.luster.avg" /></label>
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
				<label> <acme:message code="admin.dashboard.luster.stdev" /></label>
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