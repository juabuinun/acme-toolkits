
package acme.features.any.toolkits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.toolkit.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;
import acme.services.AuthoriseAll;
import acme.services.toolkit.ToolkitService;

@Service
public class AnyToolkitShowService extends AuthoriseAll<Any, Toolkit> implements AbstractShowService<Any, Toolkit> {

	@Autowired
	protected ToolkitService service;


	@Override
	@Transactional
	public Toolkit findOne(final Request<Toolkit> request) {
		return this.service.findById(request);
	}

	@Override
	@Transactional
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

}
