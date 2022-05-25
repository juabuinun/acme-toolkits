package acme.components.custom.controllers;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import acme.framework.components.models.Model;
import acme.framework.controllers.CommandManager;
import acme.framework.controllers.Errors;
import acme.framework.controllers.HttpMethod;
import acme.framework.controllers.Request;
import acme.framework.controllers.Response;
import acme.framework.exceptions.PassThroughException;
import acme.framework.helpers.Assert;
import acme.framework.helpers.HttpMethodHelper;
import acme.framework.helpers.StringHelper;
import acme.framework.helpers.ValidationHelper;
import acme.framework.roles.Any;
import acme.framework.roles.Authenticated;
import acme.framework.roles.UserRole;
import acme.framework.services.AbstractService;
import acme.framework.services.ServiceWrapper;

/**
 * Just a copy of the Abstract Controller. 
 * 
 * @author jbn
 *
 * @param <R>
 * @param <E>
 */
@Controller
public abstract class AbstractRedefinedController<R extends UserRole, E> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractRedefinedController.class);
	// Transaction management ------------------------------------------------.

	@Autowired
	protected PlatformTransactionManager				transactionManager;
	protected ThreadLocal<DefaultTransactionDefinition>	transactionDefinition;
	protected ThreadLocal<TransactionStatus>			transactionStatus;


	protected void startTransaction() {
		String name;
		DefaultTransactionDefinition localDefinition;
		TransactionStatus localStatus;

		name = UUID.randomUUID().toString();		
		AbstractRedefinedController.logger.debug("Starting transaction {}", name);
		
		localDefinition = new DefaultTransactionDefinition();
		localDefinition.setName(name);		
		localStatus = this.transactionManager.getTransaction(localDefinition);
		
		this.transactionDefinition.set(localDefinition);
		this.transactionStatus.set(localStatus);
	}

	protected void commitTransaction() {
		assert this.isTransactionActive();
		
		String name;
		DefaultTransactionDefinition localDefinition;
		TransactionStatus localStatus;
		
		localDefinition = this.transactionDefinition.get();
		name = localDefinition.getName();
		AbstractRedefinedController.logger.debug("Committing transaction {}", name);
		
		localStatus = this.transactionStatus.get();
		this.transactionStatus.remove();
		this.transactionDefinition.remove();		
		
		this.transactionManager.commit(localStatus);		
	}

	protected void rollbackTransaction() {
		assert this.isTransactionActive();
		
		String name;
		DefaultTransactionDefinition localDefinition;
		TransactionStatus localStatus;
		
		localDefinition = this.transactionDefinition.get();
		name = localDefinition.getName();
		AbstractRedefinedController.logger.debug("Rolling transaction {} back", name);
		
		localDefinition = this.transactionDefinition.get();
		localStatus = this.transactionStatus.get();
		this.transactionStatus.remove();
		this.transactionDefinition.remove();
		
		this.transactionManager.rollback(localStatus);		
	}

	protected boolean isTransactionActive() {
		boolean result;
		TransactionStatus localStatus;

		localStatus = this.transactionStatus.get();
		result = localStatus != null && !localStatus.isCompleted();

		return result;
	}

	// Command Management -----------------------------------------------------


	protected CommandManager<R, E> commandManager;


	protected void addCommand(final String command, final AbstractService<R, E> service) {
		assert !StringHelper.isBlank(command);
		assert !this.commandManager.isRegistered(command);
		assert service != null;

		this.commandManager.addCommand(command, service);
	}

	protected void addCommand(final String command, final String baseCommand, final AbstractService<R, E> service) {
		assert !StringHelper.isBlank(command);
		assert !StringHelper.isBlank(baseCommand);
		assert !this.commandManager.isRegistered(command);
		assert service != null;

		this.commandManager.addCommand(command, baseCommand, service);
	}

	// Constructor ------------------------------------------------------------


	@Autowired
	protected RequestMappingHandlerMapping	handlerMapping;

	protected Class<R>						roleClazz;
	protected Class<E>						entityClazz;

	protected String						listViewName;
	protected String						formViewName;
	protected String						requestPath;


	@SuppressWarnings("unchecked")
	protected AbstractRedefinedController() {
		Class<?>[] types;
		PropertyNamingStrategy.KebabCaseStrategy translator;
		String roleName, entityName;

		types = GenericTypeResolver.resolveTypeArguments(this.getClass(), AbstractRedefinedController.class);
		assert types != null && types.length == 2;
		this.roleClazz = (Class<R>) types[0];
		this.entityClazz = (Class<E>) types[1];

		translator = new PropertyNamingStrategy.KebabCaseStrategy();
		roleName = translator.translate(this.roleClazz.getSimpleName());
		entityName = translator.translate(this.entityClazz.getSimpleName());

		this.listViewName = String.format("%s/%s/list", roleName, entityName);
		this.formViewName = String.format("%s/%s/form", roleName, entityName);
		this.requestPath = String.format("/%s/%s/", roleName, entityName);
		
		this.transactionDefinition = new ThreadLocal<DefaultTransactionDefinition>();
		this.transactionStatus = new ThreadLocal<TransactionStatus>();
		this.commandManager = new CommandManager<R, E>();
	}

	@PostConstruct
	protected void initialiseRequestMapping() {
		Method requestHandler;
		String requestTemplate;

		try {
			requestHandler = AbstractRedefinedController.class.getMethod( //
				"handleRequest", //
				String.class, //
				Map.class, //
				HttpServletRequest.class, //
				HttpServletResponse.class, //
				Locale.class);
		} catch (final Throwable oops) {
			throw new PassThroughException(oops);
		}

		requestTemplate = String.format("%s/{command}", this.requestPath);
		this.handlerMapping.registerMapping( //
			RequestMappingInfo. //
				paths(requestTemplate). //
				methods(RequestMethod.GET, RequestMethod.POST). //
				produces(MediaType.TEXT_HTML_VALUE). //
				build(),
			this, //
			requestHandler //
		);
	}

	// Handler ----------------------------------------------------------------

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
		ServiceWrapper<R, E> service;

		result = null;
		request = null;
		response = null;
		service = null;

		servletMethod = servletRequest.getMethod();
		servletUrl = servletRequest.getRequestURI();

		AbstractRedefinedController.logger.debug("DISPATCHING {} {}", servletMethod, servletUrl);
		AbstractRedefinedController.logger.debug("Command: {}", command);
		AbstractRedefinedController.logger.debug("Model:   {}", model);
		AbstractRedefinedController.logger.debug("Locale:  {}", locale.getLanguage());

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

			// HINT: let's create a service wrapper.

			service = new ServiceWrapper<R, E>(this.commandManager.getService(command));

			// HINT: let's make sure that the principal has the appropriate role.

			if (this.roleClazz.equals(Any.class))
				;
			else if (this.roleClazz.equals(Authenticated.class))
				Assert.state(request.getPrincipal().isAuthenticated(), locale, "default.error.not-authorised");
			else
				Assert.state(request.getPrincipal().hasRole(this.roleClazz), locale, "default.error.not-authorised");
			request.getPrincipal().setActiveRole(this.roleClazz);

			// HINT: let's request authorisation from the service.

			Assert.state(service.authorise(request), locale, "default.error.not-authorised");

			// HINT: let's dispatch the request building on the HTTP method used.

			switch (request.getMethod()) {
			case GET:
				response = this.doGet(request, service);
				break;
			case POST:
				response = this.doPost(request, service);
				break;
			default:
				Assert.state(false, locale, "default.error.endpoint-unavailable");
				break;
			}
			assert response != null;
			response.getModel().setDefaultContext();

			// HINT: let's commit or roll the transaction back depending on whether there are errors or not in the response.
			// HINT+ Note that the 'onSuccess' and the 'onFailure' methods must be executed in fresh transactions.

			if (!response.hasErrors()) {
				this.commitTransaction();
				this.startTransaction();
				service.onSuccess(request, response);
				this.commitTransaction();
			} else {
				this.rollbackTransaction();
				this.startTransaction();
				service.onFailure(request, response, null);
				this.commitTransaction();
			}

			// HINT: let's build the requested view and let's add some predefined attributes to the model.

			result = this.buildRequestedView(request, response);
			result.addObject("command", command);
			result.addObject("principal", request.getPrincipal());
		} catch (final Throwable oops) {
			// HINT: if a throwable is caught, then the current transaction must be rolled back, if any,
			// HINT: the service must execute the 'onFailure' method, and the panic view must be returned.

			try {
				if (this.isTransactionActive()) {
					this.rollbackTransaction();
				}
			} catch (final Throwable ouch) {
			}
			try {
				if (service != null) {
					this.startTransaction();
					service.onFailure(request, response, oops);
					this.commitTransaction();
				}
			} catch (final Throwable ouch) {
			}
			result = this.buildPanicView(request, response, oops);
		}

		assert result != null;

		// HINT: finally, let's perform some logging and return the resulting model and view.

		AbstractRedefinedController.logger.debug("SERVING {} {}", servletMethod, servletUrl);
		AbstractRedefinedController.logger.debug("Result: {}", result);

		return result;
	}

	public Response<E> doGet(final Request<E> request, final ServiceWrapper<R, E> service) {
		assert request != null;
		assert service != null;

		Response<E> result;
		Collection<E> list;
		E entity;
		String view;
		Model model;
		Errors errors;

		view = null;
		model = null;
		errors = null;

		// HINT: let's dispatch according to the semantics of the base command.

		switch (request.getBaseCommand()) {
		case "list":
			// HINT: a LIST requests using the 'GET' method is served as follows:
			// HINT+ a) find the many objects to be shown; b) unbind the list into a fresh model.
			// HINT+ Note that no errors are expected, but exceptions might be thrown.
			list = service.findMany(request);
			view = this.listViewName;
			model = new Model();
			service.unbindMany(request, list, model);
			errors = new Errors();
			break;
		case "show":
		case "update":
		case "delete":
			// HINT: a SHOW, UPDATE, or DELETE requests using the 'GET' method are served as follows:
			// HINT+ a) find the object to be shown, updated, or deleted using the data in the request;
			// HINT+ b) unbind that object into a fresh model.  Note that no errors are expected, but
			// HINT+ exceptions might be thrown.
			entity = service.findOne(request);
			view = this.formViewName;
			model = new Model();
			service.unbindOne(request, entity, model);
			errors = new Errors();
			break;
		case "perform":
		case "create":
			// HINT: a PERFORM/CREATE request using the 'GET' method are served as follows:
			// HINT+ a) instantiate the object to create using the data in the request; b) unbind that
			// HINT+ object to a fresh model.  Note that no errors are expected, but exceptions might be thrown.
			entity = service.instantiate(request);
			view = this.formViewName;
			model = new Model();
			service.unbindOne(request, entity, model);
			errors = new Errors();
			break;
		default:
			Assert.state(false, request.getLocale(), "default.error.endpoint-unavailable");
			break;
		}

		assert !StringHelper.isBlank(view);
		assert model != null;
		assert errors != null;

		// HINT: finally, let's assemble a response and return it.

		result = new Response<E>(view, model, errors);

		return result;
	}

	public Response<E> doPost(final Request<E> request, final ServiceWrapper<R, E> service) {
		assert request != null;
		assert service != null;

		Response<E> result;
		E entity;
		String view;
		Model model;
		Errors errors;

		// HINT: the first step to handle a POST request fetches the entity to be handled.

		entity = null;
		switch (request.getBaseCommand()) {
		case "perform":
		case "create":
			// HINT: a PERFORM or CREATE request involves instantiating the appropriate entity from the
			// HINT+ request.
			entity = service.instantiate(request);
			break;
		case "update":
		case "delete":
			// HINT: an UPDATE or a DELETE request involves finding the entity to be updated or deleted.
			entity = service.findOne(request);
			break;
		default:
			Assert.state(false, request.getLocale(), "default.error.endpoint-unavailable");
			break;
		}
		assert entity != null;

		// HINT: the second step cares of performing the command on the entity prepared by the previous step.

		model = new Model();
		errors = new Errors();
		switch (request.getBaseCommand()) {
		case "perform":
			// HINT: dealing with a PERFORM request involves the following steps: a) binding the request onto
			// HINT+ the entity instantiated by the previous step; b) performing constraint validation on it;
			// HINT+ c) performing user-defined validation; d) invoking the service to perform the query if
			// HINT+ if there are not any errors; and e) unbinding the result to the output model if there
			// HINT+ are not any errors.
			service.bind(request, entity, errors);
			ValidationHelper.validate(request, entity, errors);
			service.validate(request, entity, errors);
			if (!errors.hasErrors()) {
				service.perform(request, entity, errors);
				service.unbindOne(request, entity, model);
			}
			break;
		case "create":
			// HINT: dealing with a CREATE request involves the following steps: a) binding the request onto
			// HINT+ the entity instantiated by the previous step; b) performing constraint validation on it;
			// HINT+ c) performing user-defined validation; d) if there are not any errors, then invoking the
			// HINT+ service to create the entity.
			service.bind(request, entity, errors);
			ValidationHelper.validate(request, entity, errors);
			service.validate(request, entity, errors);
			if (!errors.hasErrors()) {
				service.create(request, entity);
			}
			break;
		case "update":
			// HINT: dealing with an UPDATE request involves the following steps: a) binding the request onto
			// HINT+ the entity fetched by the previous step; b) performing constraint validation on it;
			// HINT+ c) performing user-defined validation; d) if there are not any errors, then invoking the
			// HINT+ service to update the entity.
			service.bind(request, entity, errors);
			ValidationHelper.validate(request, entity, errors);
			service.validate(request, entity, errors);
			if (!errors.hasErrors()) {
				service.update(request, entity);
			}
			break;
		case "delete":
			// HINT: dealing with a DELETE request is straightforward.
			service.delete(request, entity);
			break;
		default:
			Assert.state(false, request.getLocale(), "default.error.endpoint-unavailable");
			break;
		}

		assert model != null;
		assert errors != null;

		// HINT: the third step cares of computing the resulting view and dealing with errors.

		if (request.getBaseCommand().equals("perform")) {
			// HINT: if we're dealing with a PERFORM request, then we return the same view.
			view = this.formViewName;
		} else if (!errors.hasErrors()) {
			// HINT: if there aren't any errors, then we must redirect to the referrer view, which cares of
			// HINT+ returning to the appropriate listing or /master/welcome.
			view = "redirect:/master/referrer";
		} else {
			// HINT: if there are some errors, then we must redirect to the same view.  The model
			// HINT+ is the same, so that the user may make changes and submit the form again.
			view = this.formViewName;
			service.unbindOne(request, entity, model);
			request.transferErroneousAttributes(errors, model);
		}

		assert !StringHelper.isBlank(view);
		assert model != null;
		assert errors != null;

		// HINT: the final step assembles the response and returns it.

		result = new Response<E>(view, model, errors);

		return result;
	}

	// Internal methods -------------------------------------------------------

	protected ModelAndView buildRequestedView(final Request<E> request, final Response<E> response) {
		ModelAndView result;

		result = new ModelAndView();
		result.setStatus(HttpStatus.OK);
		result.setViewName(response.getView());

		for (final Entry<String, Object> entry : response.getModel()) {
			String key;
			Object value;

			key = entry.getKey();
			value = entry.getValue();
			result.addObject(key, value);
		}

		if (response.hasErrors()) {
			for (final Entry<String, List<String>> entry : response.getErrors()) {
				String name;
				List<String> messages;
				String text;

				name = String.format("%s$error", entry.getKey());
				messages = entry.getValue();
				text = StringHelper.toString(messages, ". ", ".");
				result.addObject(name, text);
			}
		}

		return result;
	}

	protected ModelAndView buildPanicView(final Request<E> request, final Response<E> response, final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView();
		result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		result.setViewName("master/panic");
		result.addObject("oops", oops);

		return result;
	}

}
