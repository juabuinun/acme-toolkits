package acme.services.chimpum;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.chimpum.Chimpum;
import acme.form.chimpum.ChimpumDto;
import acme.form.chimpum.SaveChimpumDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.repositories.ChimpumRepository;
import acme.services.AbstractCrudServiceImpl;
import acme.services.config.AcmeConfigurationService;
import acme.services.item.ItemService;

@Service
@Transactional
public class ChimpumService extends AbstractCrudServiceImpl<Chimpum,ChimpumRepository>{
	
	@Autowired
	protected ModelMapper mapper;
	
	@Autowired
	protected ItemService itemService;
	
	@Autowired
	protected AcmeConfigurationService configService;
	
	@Autowired
	protected ChimpumService(final ChimpumRepository repo) {
		super(repo);
	}
	
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		final ChimpumDto dto = this.mapper.map(entity, ChimpumDto.class);
		request.unbind(entity, model, BindHelper.getAllFieldNames(ChimpumDto.class));
		model.setAttribute("duration", dto.getDuration());
		model.setAttribute("itemId", entity.getItem().getId());
	}

	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		request.bind(entity, errors, BindHelper.getAllFieldNames(SaveChimpumDto.class));
		entity.setItem(this.itemService.findById(request.getModel().getInteger("itemId")));
	}
	
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		this.configService.checkMoney(request, errors, entity.getBudget(), "budget");
		errors.state(request, LocalDate.now().plus(1,ChronoUnit.MONTHS).isBefore(entity.getStartDate().plus(1,ChronoUnit.DAYS)), "startDate", "errors.date.start.ahead.month");
		errors.state(request, entity.getStartDate().plus(1,ChronoUnit.WEEKS).isBefore(entity.getEndDate().plus(1,ChronoUnit.DAYS)), "startDate", "errors.date.end.ahead.week");
	}
}
