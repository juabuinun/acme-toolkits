<%--
- menu.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"
	import="acme.framework.helpers.PrincipalHelper,acme.roles.Inventor,acme.roles.Patron"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>

		<!--CHIRPS -->
		<acme:menu-option code="master.menu.chirp">
			<acme:menu-suboption code="master.menu.chirp.any.list"
				action="/any/chirp/list-recent" />
			<acme:menu-suboption code="master.menu.chirp.any.create"
				action="/any/chirp/create" />
		</acme:menu-option>
		<!-- USER ACCOUNTS -->
		<acme:menu-option code="master.menu.accounts">
			<acme:menu-suboption code="master.menu.accounts.any.list"
				action="/any/user-account/list-enabled" />
		</acme:menu-option>
		<!-- ANNOUNCEMENTS -->
		<acme:menu-option code="master.menu.announcements"
			access="isAuthenticated()">
			<acme:menu-suboption
				code="master.menu.announcements.authenticated.list"
				action="/authenticated/announcement/list" />
			<security:authorize access="hasRole('Administrator')">
				<acme:menu-separator />
				<acme:menu-suboption code="master.menu.announcements.admin.create"
					action="/administrator/announcement/create" />
			</security:authorize>
		</acme:menu-option>
		<!-- COMPONENTS -->
		<acme:menu-option code="master.menu.components">
			<acme:menu-suboption code="master.menu.components.any.list"
				action="/any/item/list-component" />
			<security:authorize access="hasRole('Inventor')">
				<acme:menu-separator />
				<acme:menu-suboption code="master.menu.components.inventor.list"
					action="/inventor/item/list-mine-component" />
				<acme:menu-suboption code="master.menu.components.inventor.create"
					action="/inventor/item/create-component" />
			</security:authorize>
		</acme:menu-option>
		<!-- TOOLS -->
		<acme:menu-option code="master.menu.tools">
			<acme:menu-suboption code="master.menu.tools.any.list"
				action="/any/item/list-tool" />
			<security:authorize access="hasRole('Inventor')">
				<acme:menu-separator />
				<acme:menu-suboption code="master.menu.tools.inventor.list"
					action="/inventor/item/list-mine-tool" />
				<acme:menu-suboption code="master.menu.tools.inventor.create"
					action="/inventor/item/create-tool" />
			</security:authorize>
		</acme:menu-option>
		<!-- TOOLKITS -->
		<acme:menu-option code="master.menu.toolkits">
			<acme:menu-suboption code="master.menu.toolkits.any.list"
				action="/any/toolkit/list" />
			<security:authorize access="hasRole('Inventor')">
				<acme:menu-separator />
				<acme:menu-suboption code="master.menu.toolkits.inventor.list"
					action="/inventor/toolkit/list-mine" />
				<acme:menu-suboption code="master.menu.toolkits.inventor.create"
					action="/inventor/toolkit/create" />
			</security:authorize>
		</acme:menu-option>
		<!-- PATRONAGES -->
		<acme:menu-option code="master.menu.patronage"
			access="hasAnyRole('Inventor','Patron')">
			<acme:menu-suboption code="master.menu.patronage.inventor.list"
				access="hasRole('Inventor')" action="/inventor/patronage/list-mine" />
			<acme:menu-suboption code="master.menu.patronage.patron.list"
				access="hasRole('Patron')" action="/patron/patronage/list-mine" />
		</acme:menu-option>
		
		<!-- CHIMPUMS -->
		<acme:menu-option code="master.menu.chimpum"
			access="hasRole('Inventor')">
			<acme:menu-suboption code="master.menu.chimpum.list"
				action="/inventor/chimpum/list-mine" />
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator"
			access="hasRole('Administrator')">
			<!-- DEFAULT STUFF -->
			<acme:menu-suboption code="master.menu.administrator.user-accounts"
				action="/administrator/user-account/list" />
			<acme:menu-separator />
			<acme:menu-suboption
				code="master.menu.administrator.populate-initial"
				action="/administrator/populate-initial" />
			<acme:menu-suboption code="master.menu.administrator.populate-sample"
				action="/administrator/populate-sample" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.shut-down"
				action="/administrator/shut-down" />
			<acme:menu-separator />
			<!-- CONFIG -->
			<acme:menu-suboption code="master.menu.administrator.config"
				action="/administrator/configuration/show" />

		</acme:menu-option>

	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up"
			action="/anonymous/user-account/create" access="isAnonymous()" />
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in"
			access="isAnonymous()" />

		<acme:menu-option code="master.menu.user-account"
			access="isAuthenticated()">
			<!-- GENERAL DATA -->
			<acme:menu-suboption code="master.menu.user-account.general-data"
				action="/authenticated/user-account/update" />
			<!-- PATRON -->
			<acme:menu-suboption code="master.menu.user-account.become-patron"
				action="/authenticated/patron/create" access="!hasRole('Patron')" />
			<acme:menu-suboption code="master.menu.user-account.patron"
				action="/authenticated/patron/update" access="hasRole('Patron')" />
			<!-- INVENTOR -->
			<acme:menu-suboption code="master.menu.user-account.become-inventor"
				action="/authenticated/inventor/create"
				access="!hasRole('Inventor')" />
			<acme:menu-suboption code="master.menu.user-account.inventor"
				action="/authenticated/inventor/update" access="hasRole('Inventor')" />
			<!-- DASHBOARD -->
			<security:authorize access="hasRole('Administrator')">
				<acme:menu-separator />
				<acme:menu-suboption code="master.menu.admin.dashboard"
					action="/administrator/admin-dashboard/show" />
			</security:authorize>
			<security:authorize access="hasRole('Patron')">
				<acme:menu-separator />
				<acme:menu-suboption code="master.menu.patron.dashboard"
					action="/patron/patron-dashboard/show" />
			</security:authorize>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out"
			action="/master/sign-out" access="isAuthenticated()" />
	</acme:menu-right>
</acme:menu-bar>

