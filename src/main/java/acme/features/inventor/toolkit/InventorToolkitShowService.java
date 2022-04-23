
package acme.features.inventor.toolkit;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.toolkit.Toolkit;
import acme.form.toolkit.ToolkitDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.repositories.ToolkitRepository;
import acme.roles.Inventor;

@Service
public class InventorToolkitShowService implements AbstractShowService<Inventor, Toolkit> {

	@Autowired
	protected ModelMapper mapper;
	
	@Autowired
	protected ToolkitRepository repo;


	@Override
	public boolean authorise(final Request<Toolkit> request) {
		return true;
	}

	@Override
	public Toolkit findOne(final Request<Toolkit> request) {
		final Optional<Toolkit> res = this.repo.findById(request.getModel().getInteger("id"));
		return res.isPresent() ? res.get() : null;
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		final ToolkitDto dto = this.mapper.map(entity, ToolkitDto.class);
		request.unbind(dto, model,"id","version","code","title","price","description","notes","info");
	}

}
