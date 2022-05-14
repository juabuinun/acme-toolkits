
package acme.components.custom.controllers;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import acme.components.custom.service.CustomServiceWrapper;
import acme.framework.controllers.HttpMethod;
import acme.framework.controllers.Request;
import acme.framework.controllers.Response;
import acme.framework.helpers.Assert;
import acme.framework.helpers.HttpMethodHelper;
import acme.framework.roles.Any;
import acme.framework.roles.Authenticated;
import acme.framework.roles.UserRole;

/**
 * Custom working of the abstract controller
 * 
 * @author jbn
 *
 * @param <R>
 * @param <E>
 */
@Primary
@Controller
public abstract class AcmeAbstractController<R extends UserRole, E> extends RedefinedAbstractController<R, E> {

	private static final Logger logger = LoggerFactory.getLogger(AcmeAbstractController.class);

	@Override
	public ModelAndView handleRequest( //
		@PathVariable("command") final String command, //
		@RequestParam final Map<String, Object> model, //
		final HttpServletRequest servletRequest, //
		final HttpServletResponse servletResponse, //
		final Locale locale) {

		ModelAndView result;
		String servletMethod, servletUrl;
		HttpMethod method;
		String baseCommand;
		Request<E> request;
		Response<E> response;
		//use custom wrapper
		CustomServiceWrapper<R, E> service;

		result = null;
		request = null;
		response = null;
		service = null;

		servletMethod = servletRequest.getMethod();
		servletUrl = servletRequest.getRequestURI();

		AcmeAbstractController.logger.debug("DISPATCHING {} {}", servletMethod, servletUrl);
		AcmeAbstractController.logger.debug("Command: {}", command);
		AcmeAbstractController.logger.debug("Model:   {}", model);
		AcmeAbstractController.logger.debug("Locale:  {}", locale.getLanguage());

		try {
			// HINT: let's start a new transaction.

			this.startTransaction();

			// HINT: let's deal with CSRF hacking.

			if (model.containsKey("_csrf")) {
				// HINT: Remove it so that a new token is generated automatically
				model.remove("_csrf");
			}

			// HINT: let's make sure that the command is available.

			method = HttpMethodHelper.parse(servletMethod);
			Assert.state(this.commandManager.isRegistered(command), locale, "default.error.endpoint-unavailable");
			baseCommand = this.commandManager.getBaseCommand(command);

			// HINT: let's create the request object.

			request = new Request<E>( //
				method, command, baseCommand, //
				model, locale, //
				servletRequest, servletResponse);

			// HINT: let's make sure that the principal has the appropriate role.
			this.checkRoles(request, locale);
			
			// HINT: let's request authorisation from the service.

			service = new CustomServiceWrapper<R, E>(this.commandManager.getService(command));
			Assert.state(service.authorise(request), locale, "default.error.not-authorised");

			// HINT: let's dispatch the request building on the HTTP method used.
			// HINT: realise that the dispatcher method is invoked through the 'self' reference to this
			// HINT+ controller because they must be executed within the current transaction.

			switch (request.getMethod()) {
			case GET:
				response = this.self.doGet(request, service);
				break;
			case POST:
				response = this.self.doPost(request, service);
				break;
			default:
				Assert.state(false, locale, "default.error.endpoint-unavailable");
				break;
			}
			assert response != null;
			response.getModel().setDefaultContext();

			// HINT: let's commit or rollback the transaction depending on whether there are errors or not in the response.
			// HINT+ note that the 'onSuccess' and the 'onFailure' methods must be executed in fresh transactions.

			if (!response.hasErrors()) {
				this.commitTransaction();
				this.startTransaction();
				service.onSuccess(request, response);
			} else {
				this.rollbackTransaction();
				this.startTransaction();
				service.onFailure(request, response, null);
			}
			this.commitTransaction();

			// HINT: let's build the requested view and let's add some predefined attributes to the model.

			result = this.buildRequestedView(request, response);
			result.addObject("command", command);
			result.addObject("principal", request.getPrincipal());
		} catch (final Throwable oops) {
			// HINT: if a throwable is caught, then the current transaction must be rollbacked, if any,
			// HINT: the service must execute the 'onFailure' method, and the panic view must be returned.

			if (this.isTransactionActive()) {
				this.rollbackTransaction();
			}
			if (service != null) {
				this.startTransaction();
				service.onFailure(request, response, oops);
				this.commitTransaction();
			}
			result = this.buildPanicView(request, response, oops);
			AcmeAbstractController.logger.error("Error on custom controller", oops);
		}

		// HINT: must always return a 'ModelAndView' object, be it the user-defined one or a panic one.

		assert result != null;

		AcmeAbstractController.logger.debug("SERVING {} {}", servletMethod, servletUrl);
		AcmeAbstractController.logger.debug("Result: {}", result);

		return result;
	}
	
	protected void checkRoles(final Request<E> request, final Locale locale) {
		if (this.roleClazz.equals(Any.class))
			;
		else if (this.roleClazz.equals(Authenticated.class))
			Assert.state(request.getPrincipal().isAuthenticated(), locale, "default.error.not-authorised");
		else
			Assert.state(request.getPrincipal().hasRole(this.roleClazz), locale, "default.error.not-authorised");
		request.getPrincipal().setActiveRole(this.roleClazz);
	}
	
	

}
