package acme.features.inventor.luster;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.luster.Luster;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;
import acme.services.AbstractAuthoriseAll;
import acme.services.luster.LusterService;

@Service
@Transactional
public class InventorLusterListService extends AbstractAuthoriseAll<Inventor,Luster> implements AbstractListService<Inventor,Luster>{

	@Autowired
	protected LusterService service;

	@Override
	public Collection<Luster> findMany(final Request<Luster> request) {
		return this.service.findAllByPrincipalInventor();
	}

	@Override
	public void unbind(final Request<Luster> request, final Luster entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

}
