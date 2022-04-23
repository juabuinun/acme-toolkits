package acme.features.any.toolkits;

import java.util.Collection;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.toolkit.Toolkit;
import acme.form.toolkit.ToolkitDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.repositories.ToolkitRepository;

@Service
@Transactional
public class AnyToolkitListService implements AbstractListService<Any,Toolkit>{

	@Autowired
	protected ModelMapper mapper;
	
	@Autowired
	protected ToolkitRepository repo;
	
	@Override
	public boolean authorise(final Request<Toolkit> request) {
		return true;
	}

	@Override
	@Transactional
	public Collection<Toolkit> findMany(final Request<Toolkit> request) {
		return this.repo.findAll();
	}

	@Override
	@Transactional
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		final ToolkitDto dto = this.mapper.map(entity, ToolkitDto.class);
		request.unbind(dto, model, "id","version","code","title","price");
	}

}
