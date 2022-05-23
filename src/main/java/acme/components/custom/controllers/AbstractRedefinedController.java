package acme.components.custom.controllers;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
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
public abstract class AbstractRedefinedController<R extends UserRole, E> implements RedefinedController<R,E> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractRedefinedController.class);
	
	@Autowired
	protected AbstractRedefinedController<R, E>		self;

	protected Class<R>						roleClazz;
	protected Class<E>						entityClazz;

	protected String						listViewName;
	protected String						formViewName;
	protected String						requestPath;

	protected CommandManager<R, E>			commandManager;

	@Autowired
	protected RequestMappingHandlerMapping	handlerMapping;

	// Transaction management ------------------------------------------------.

	@Autowired
	protected PlatformTransactionManager	transactionManager;
	protected TransactionStatus				transactionStatus;


	protected void startTransaction() {
		TransactionDefinition transactionDefinition;

		transactionDefinition = new DefaultTransactionDefinition();
		this.transactionStatus = this.transactionManager.getTransaction(transactionDefinition);
	}

	protected void commitTransaction() {
		assert this.isTransactionActive();

		this.transactionManager.commit(this.transactionStatus);
		this.transactionStatus = null;
	}


	protected void rollbackTransaction() {
		assert this.isTransactionActive();

		this.transactionManager.rollback(this.transactionStatus);
		this.transactionStatus = null;
	}


	protected boolean isTransactionActive() {
		boolean result;

		result = this.transactionStatus != null && !this.transactionStatus.isCompleted();

		return result;
	}

	// Command Management -----------------------------------------------------

	@Override
	public void addCommand(final String command, final AbstractService<R, E> service) {
		assert !StringHelper.isBlank(command);
		assert !this.commandManager.isRegistered(command);
		assert service != null;

		this.commandManager.addCommand(command, service);
	}

	@Override
	public void addCommand(final String command, final String baseCommand, final AbstractService<R, E> service) {
		assert !StringHelper.isBlank(command);
		assert !StringHelper.isBlank(baseCommand);
		assert !this.commandManager.isRegistered(command);
		assert service != null;

		this.commandManager.addCommand(command, baseCommand, service);
	}

	// Constructor ------------------------------------------------------------

	@SuppressWarnings("unchecked")
	protected AbstractRedefinedController() {
		Class<?>[] types;
		PropertyNamingStrategy.KebabCaseStrategy translator;
		String roleName, entityName;

		types = GenericTypeResolver.resolveTypeArguments(this.getClass(), AbstractRedefinedController.class);

		if (types == null || types.length != 2) {
			System.err.printf("I'm sorry, %s cannot be instantiated.%n", this.getClass().getName());
			System.err.printf("I can't resolve its generic types.%n");
			System.exit(1);
		}

		this.roleClazz = (Class<R>) types[0];
		this.entityClazz = (Class<E>) types[1];

		translator = new PropertyNamingStrategy.KebabCaseStrategy();
		roleName = translator.translate(this.roleClazz.getSimpleName());
		entityName = translator.translate(this.entityClazz.getSimpleName());

		this.listViewName = String.format("%s/%s/list", roleName, entityName);
		this.formViewName = String.format("%s/%s/form", roleName, entityName);

		this.commandManager = new CommandManager<R, E>();

		this.requestPath = String.format("/%s/%s/", roleName, entityName);
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

			// HINT: let's make sure that the principal has the appropriate role.

			if (this.roleClazz.equals(Any.class))
				;
			else if (this.roleClazz.equals(Authenticated.class))
				Assert.state(request.getPrincipal().isAuthenticated(), locale, "default.error.not-authorised");
			else
				Assert.state(request.getPrincipal().hasRole(this.roleClazz), locale, "default.error.not-authorised");
			request.getPrincipal().setActiveRole(this.roleClazz);

			// HINT: let's request authorisation from the service.

			service = new ServiceWrapper<R, E>(this.commandManager.getService(command));
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
			AbstractRedefinedController.logger.error("Error handling request",oops);
		}

		// HINT: must always return a 'ModelAndView' object, be it the user-defined one or a panic one.

		assert result != null;

		AbstractRedefinedController.logger.debug("SERVING {} {}", servletMethod, servletUrl);
		AbstractRedefinedController.logger.debug("Result: {}", result);

		return result;
	}

	@Override
	@Transactional(TxType.MANDATORY)
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

		// HINT: unless an exception is thrown, the previous statements must produce a view name, a model, and an errors object.

		assert !StringHelper.isBlank(view);
		assert model != null;
		assert errors != null;

		result = new Response<E>(view, model, errors);

		return result;
	}

	@Override
	@Transactional(TxType.MANDATORY)
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

		// HINT: the second step cares of performing the command on the entity fetched by the previous step.

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
			// HINT: dealing with a DELETE request involves validating that the entity can be deleted
			// HINT+ and then invoking the service to delete the entity.
			service.validate(request, entity, errors);
			if (!errors.hasErrors()) {
				service.delete(request, entity);
			}
			break;
		default:
			Assert.state(false, request.getLocale(), "default.error.endpoint-unavailable");
			break;
		}

		assert model != null;
		assert errors != null;

		if (request.getBaseCommand().equals("perform")) {
			// HINT: if we're dealing with a PERFORM request, then we return the same view and
			// HINT+ reset the model if there are any errors.
			view = this.formViewName;
			//			if (errors.hasErrors()) {
			//				model.append(request.getModel());
			//			}
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

		// HINT: unless an exception is thrown, the previous statements must produce a view name, a model,
		// HINT+ and an errors object.

		assert !StringHelper.isBlank(view);
		assert model != null;
		assert errors != null;

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

		// INFO: errors are not bound if the model is a list, which prevents editing lists inline.
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
