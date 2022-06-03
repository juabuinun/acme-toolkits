package acme.features.inventor.chimpum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.chimpum.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;
import acme.services.AbstractAuthoriseAll;
import acme.services.chimpum.ChimpumService;

@Service
@Transactional
public class InventorChimpumListService extends AbstractAuthoriseAll<Inventor,Chimpum> implements AbstractListService<Inventor,Chimpum>{

	@Autowired
	protected ChimpumService service;

	@Override
	public Collection<Chimpum> findMany(final Request<Chimpum> request) {
		return this.service.findAllByPrincipalInventor();
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

}
