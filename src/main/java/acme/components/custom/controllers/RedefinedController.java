package acme.components.custom.controllers;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import acme.framework.controllers.Request;
import acme.framework.controllers.Response;
import acme.framework.roles.UserRole;
import acme.framework.services.AbstractService;
import acme.framework.services.ServiceWrapper;

public interface RedefinedController<R extends UserRole, E> {

	void addCommand(final String command, final AbstractService<R, E> service);

	void addCommand(String command, String baseCommand, AbstractService<R, E> service);

	ModelAndView handleRequest(String command, Map<String, Object> model, HttpServletRequest servletRequest, HttpServletResponse servletResponse, Locale locale);

	Response<E> doGet(Request<E> request, ServiceWrapper<R, E> service);

	Response<E> doPost(Request<E> request, ServiceWrapper<R, E> service);


}
