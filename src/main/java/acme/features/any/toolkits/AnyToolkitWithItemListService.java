
package acme.features.any.toolkits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.toolkit.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.services.AuthoriseAll;
import acme.services.toolkit.ToolkitService;

@Service
public class AnyToolkitWithItemListService extends AuthoriseAll<Any, Toolkit> implements AbstractListService<Any, Toolkit> {

	@Autowired
	protected ToolkitService service;


	@Override
	@Transactional
	public Collection<Toolkit> findMany(final Request<Toolkit> request) {
		return this.service.findByItem(request, true);
	}

	@Override
	@Transactional
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		this.service.unbindListingRecord(request, entity, model);
	}

}
