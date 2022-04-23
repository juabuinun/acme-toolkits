package acme.features.inventor.toolkit;

import java.util.Collection;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Specifications;
import acme.entities.toolkit.Toolkit;
import acme.form.toolkit.ToolkitDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractListService;
import acme.repositories.ToolkitRepository;
import acme.roles.Inventor;

@Service
@Transactional
public class InventorToolkitListService implements AbstractListService<Inventor,Toolkit>{

	@Autowired
	protected ModelMapper mapper;
	
	@Autowired
	protected ToolkitRepository repo;
	
	@Override
	public boolean authorise(final Request<Toolkit> request) {
		return PrincipalHelper.get().hasRole(Inventor.class);
	}

	@Override
	@Transactional
	public Collection<Toolkit> findMany(final Request<Toolkit> request) {
		return this.repo.findAll(Specifications.toolkitPrincipalIsOwner());
	}

	@Override
	@Transactional
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		final ToolkitDto dto = this.mapper.map(entity, ToolkitDto.class);
		request.unbind(dto, model, "id","version","code","title","price");
	}


}
