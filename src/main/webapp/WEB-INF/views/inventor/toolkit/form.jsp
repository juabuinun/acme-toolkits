
<%@page language="java"
	import="acme.framework.helpers.MessageHelper,java.util.Collection, java.util.ArrayList, java.util.Map, javax.servlet.jsp.tagext.JspFragment"%>

<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${draftMode == false}">

	<acme:input-textbox code="inventor.toolkit.code" path="code"
		placeholder="code.placeholder" readonly="${command != 'create'}" />
	<acme:input-textbox code="inventor.toolkit.name" path="title" />
	<acme:input-textbox code="inventor.toolkit.description"
		path="description" />
	<acme:input-money code="inventor.toolkit.price" path="price"
		readonly="true" />
	<acme:input-textarea code="inventor.toolkit.notes" path="notes" />
	<acme:input-textbox code="inventor.toolkit.info" path="info" placeholder="default.placeholder.url"/>

	<jstl:choose>
		<jstl:when test="${draftMode == true}">
			<input id="available_components_input"
				name="available_components_input" type="hidden">
			<input id="binded_components_input" name="binded_components_input"
				type="hidden">

			<div class="form-group">
				<div class="col">
					<div class="row">
						<label><acme:message
								code="inventor.toolkit.item.component" /></label>
					</div>
					<div class="row">
						<div class="col">
							<div class=" card">
								<div class="card-header">
									<acme:message code="inventor.toolkit.item.available" />
								</div>
								<div id="available_components_collection" class="card-body">
									<jstl:forEach items="${availableComponents}" var="component"
										varStatus="loop">
										<div class="col m-2">
											<label id="component_${component.item.id}"
												class="btn btn-warning btn-component-select text-left">
												<span> <strong><jstl:out
															value="${component.item.name}"></jstl:out></strong>
											</span> <br> <jstl:out value="${component.item.code}"></jstl:out>
												<br> <jstl:out value="${component.item.description}"></jstl:out>
												<br> <acme:message code="inventor.toolkit.item.tech" />:&nbsp;<jstl:out
													value="${component.item.technology}"></jstl:out> <br>
												<acme:message code="inventor.toolkit.item.price" />:&nbsp;<jstl:out
													value="${component.item.price}"></jstl:out> <br> <acme:message
													code="inventor.toolkit.item.quantity" />:&nbsp;<input
												id="component_${component.item.id}_qty"
												name="component_${component.item.id}_qty" type="text"
												class="form-control qty-input" style="width: 60px"
												value="${component.quantity}">
											</label>
										</div>
									</jstl:forEach>
								</div>
							</div>
						</div>
						<div class="col-2">
							<div class="row">
								<div class="col text-center m-1 ">
									<button id="btn-bind-all-components" type="button"
										class="btn btn-light">
										<span class="fa fa-angle-double-right"></span>
									</button>
								</div>
							</div>
							<div class="row">
								<div class="col text-center m-1 ">
									<button id="btn-bind-selected-components" type="button"
										class="btn btn-light">
										<span class="fa fa-angle-right"></span>
									</button>
								</div>
							</div>
							<div class="row">
								<div class="col text-center m-1">
									<button id="btn-unbind-selected-components" type="button"
										class="btn btn-light">
										<span class="fa fa-angle-left"></span>
									</button>
								</div>
							</div>
							<div class="row">
								<div class="col text-center m-1 ">
									<button id="btn-unbind-all-components" type="button"
										class="btn btn-light">
										<span class="fa fa-angle-double-left"></span>
									</button>
								</div>
							</div>
						</div>
						<div class="col">
							<div class=" card">
								<div class="card-header">
									<acme:message code="inventor.toolkit.item.binded" />
								</div>
								<div id="binded_components_collection" class="card-body">
									<jstl:forEach items="${bindedComponents}" var="component"
										varStatus="loop">
										<div class="col m-2">
											<label id="component_${component.item.id}"
												class="btn btn-warning btn-component-select text-left">
												<span> <strong><jstl:out
															value="${component.item.name}"></jstl:out></strong>
											</span> <br> <jstl:out value="${component.item.code}"></jstl:out>
												<br> <jstl:out value="${component.item.description}"></jstl:out>
												<br> <acme:message code="inventor.toolkit.item.tech" />:&nbsp;<jstl:out
													value="${component.item.technology}"></jstl:out> <br>
												<acme:message code="inventor.toolkit.item.price" />:&nbsp;<jstl:out
													value="${component.item.price}"></jstl:out> <br> <acme:message
													code="inventor.toolkit.item.quantity" />:&nbsp;<input
												id="component_${component.item.id}_qty"
												name="component_${component.item.id}_qty" type="text"
												class="form-control qty-input" readonly style="width: 60px"
												value="${component.quantity}" />
											</label>
										</div>
									</jstl:forEach>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- TOOLS =================================================================================== -->

			<input id="available_tools_input" name="available_tools_input"
				type="hidden">
			<input id="binded_tools_input" name="binded_tools_input"
				type="hidden">

			<div class="form-group">
				<div class="col">
					<div class="row">
						<label><acme:message code="inventor.toolkit.item.tool" /></label>
					</div>
					<div class="row">
						<div class="col">
							<div class=" card">
								<div class="card-header">
									<acme:message code="inventor.toolkit.item.available" />
								</div>
								<div id="available_tools_collection" class="card-body">
									<jstl:forEach items="${availableTools}" var="tool"
										varStatus="loop">
										<div class="col m-2">
											<label id="tool_${tool.item.id}"
												class="btn btn-warning btn-tool-select text-left"> <span>
													<strong><jstl:out value="${tool.item.name}"></jstl:out></strong>
											</span> <br> <jstl:out value="${tool.item.code}"></jstl:out> <br>
												<jstl:out value="${tool.item.description}"></jstl:out> <br>
												<acme:message code="inventor.toolkit.item.tech" />:&nbsp;<jstl:out
													value="${tool.item.technology}"></jstl:out> <br> <acme:message
													code="inventor.toolkit.item.price" />:&nbsp;<jstl:out
													value="${tool.item.price}"></jstl:out> <br> <acme:message
													code="inventor.toolkit.item.quantity" />:&nbsp;<input
												id="tool_${tool.item.id}_qty"
												name="tool_${tool.item.id}_qty" type="text"
												class="form-control qty-input" style="width: 60px"
												value="${tool.quantity}" />
											</label>
										</div>
									</jstl:forEach>
								</div>
							</div>
						</div>
						<div class="col-2">
							<div class="row">
								<div class="col text-center m-1 ">
									<button id="btn-bind-all-tools" type="button"
										class="btn btn-light">
										<span class="fa fa-angle-double-right"></span>
									</button>
								</div>
							</div>
							<div class="row">
								<div class="col text-center m-1 ">
									<button id="btn-bind-selected-tools" type="button"
										class="btn btn-light">
										<span class="fa fa-angle-right"></span>
									</button>
								</div>
							</div>
							<div class="row">
								<div class="col text-center m-1">
									<button id="btn-unbind-selected-tools" type="button"
										class="btn btn-light">
										<span class="fa fa-angle-left"></span>
									</button>
								</div>
							</div>
							<div class="row">
								<div class="col text-center m-1 ">
									<button id="btn-unbind-all-tools" type="button"
										class="btn btn-light">
										<span class="fa fa-angle-double-left"></span>
									</button>
								</div>
							</div>
						</div>
						<div class="col">
							<div class=" card">
								<div class="card-header">
									<acme:message code="inventor.toolkit.item.binded" />
								</div>
								<div id="binded_tools_collection" class="card-body">
									<jstl:forEach items="${bindedTools}" var="tool"
										varStatus="loop">
										<div class="col m-2">
											<label id="tool_${tool.item.id}"
												class="btn btn-warning btn-tool-select text-left"> <span>
													<strong><jstl:out value="${tool.item.name}"></jstl:out></strong>
											</span> <br> <jstl:out value="${tool.item.code}"></jstl:out> <br>
												<jstl:out value="${tool.item.description}"></jstl:out> <br>
												<acme:message code="inventor.toolkit.item.tech" />:&nbsp;<jstl:out
													value="${tool.item.technology}"></jstl:out> <br> <acme:message
													code="inventor.toolkit.item.price" />:&nbsp;<jstl:out
													value="${tool.item.price}"></jstl:out> <br> <acme:message
													code="inventor.toolkit.item.quantity" />:&nbsp;<input
												id="tool_${tool.item.id}_qty"
												name="tool_${tool.item.id}_qty" type="text"
												class="form-control qty-input" readonly style="width: 60px"
												value="${tool.quantity}" />
											</label>
										</div>
									</jstl:forEach>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- the other stuff ========================================================================= -->
			<jstl:choose>
				<jstl:when
					test="${command == 'show' || command=='publish' || command=='delete' || command == 'update'}">
					<acme:submit code="inventor.toolkit.update"
						action="/inventor/toolkit/update" />
					<acme:submit code="inventor.toolkit.publish"
						action="/inventor/toolkit/publish" />
					<acme:submit code="inventor.toolkit.delete"
						action="/inventor/toolkit/delete" />
				</jstl:when>
				<jstl:when test="${command == 'create'}">
					<acme:submit code="inventor.toolkit.create"
						action="/inventor/toolkit/create" />
				</jstl:when>
			</jstl:choose>
		</jstl:when>
		<jstl:when test="${draftMode == false}">
			<acme:button code="inventor.toolkit.tools"
				action="/any/item/list-tool-toolkit?masterId=${id}" />
			<acme:button code="inventor.toolkit.components"
				action="/any/item/list-component-toolkit?masterId=${id}" />
		</jstl:when>
	</jstl:choose>
</acme:form>

<script type="text/javascript" src="js/toolkitForm.js"></script>