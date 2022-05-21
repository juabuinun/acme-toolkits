package acme.features.any.toolkits;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.toolkit.Toolkit;
import acme.form.toolkit.BasicToolkitDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.services.AuthoriseAll;
import acme.services.toolkit.ToolkitService;

@Service
@Transactional
public class AnyToolkitListService extends AuthoriseAll<Any,Toolkit> implements AbstractListService<Any,Toolkit>{

	@Autowired
	protected ModelMapper mapper;
	
	@Autowired
	protected ToolkitService service;

	@Override
	public Collection<Toolkit> findMany(final Request<Toolkit> request) {
		return this.service.findAllPublished();
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		request.unbind(this.mapper.map(entity, BasicToolkitDto.class), model, BindHelper.getAllFieldNames(BasicToolkitDto.class));
	}

}
