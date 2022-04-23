<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:input-textbox code="config.currency.default" path="defaultCurrency"/>
	<acme:input-textbox code="config.currency.accept" path="acceptedCurrencies"/>
	<acme:input-textbox code="config.spam.tresh.strong" path="strongSpamThreshold"/>
	<acme:input-textbox code="config.spam.tresh.weak" path="weakSpamThreshold"/>
	
	<acme:button code="config.spam" action="/administrator/spam-word/list"/>
</acme:form>
