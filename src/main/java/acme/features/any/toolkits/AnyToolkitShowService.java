
package acme.features.any.toolkits;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.toolkit.Toolkit;
import acme.form.toolkit.ToolkitDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;
import acme.services.AuthoriseAll;
import acme.services.toolkit.ToolkitService;

@Service
public class AnyToolkitShowService extends AuthoriseAll<Any, Toolkit> implements AbstractShowService<Any, Toolkit> {

	@Autowired
	protected ModelMapper mapper;
	
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
		final ToolkitDto dto = this.mapper.map(entity, ToolkitDto.class);
		request.unbind(dto, model, BindHelper.getAllFieldNames(ToolkitDto.class));
	}

}
