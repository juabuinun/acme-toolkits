package acme.features.inventor.toolkit;

import java.util.Collection;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Specifications;
import acme.components.util.BindHelper;
import acme.entities.toolkit.Toolkit;
import acme.form.toolkit.BasicToolkitDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;
import acme.services.AuthoriseAll;
import acme.services.toolkit.ToolkitService;

@Service
@Transactional
public class InventorToolkitListService extends AuthoriseAll<Inventor,Toolkit> implements AbstractListService<Inventor,Toolkit>{

	@Autowired
	protected ModelMapper mapper;
	
	@Autowired
	protected ToolkitService service;

	@Override
	@Transactional
	public Collection<Toolkit> findMany(final Request<Toolkit> request) {
		return this.service.findBySpecification(Specifications.toolkitPrincipalIsOwner());
	}

	@Override
	@Transactional
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		request.unbind(this.mapper.map(entity, BasicToolkitDto.class), model, BindHelper.getAllFieldNames(BasicToolkitDto.class));
	}


}
