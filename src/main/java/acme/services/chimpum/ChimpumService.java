
package acme.services.chimpum;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.function.Consumer;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.Specifications;
import acme.components.util.BindHelper;
import acme.entities.chimpum.Chimpum;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.form.chimpum.ChimpumDto;
import acme.form.chimpum.NewChimpumDto;
import acme.form.chimpum.SaveChimpumDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.helpers.MessageHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.repositories.InventorRepository;
import acme.repositories.chimpum.ChimpumJpaRepository;
import acme.roles.Inventor;
import acme.services.AbstractCrudServiceImpl;
import acme.services.config.AcmeConfigurationService;
import acme.services.item.ItemService;

@Service
@Transactional
public class ChimpumService extends AbstractCrudServiceImpl<Chimpum, ChimpumJpaRepository> {

	private static final Logger			logger	= LoggerFactory.getLogger(ChimpumService.class);

	@Autowired
	protected ModelMapper				mapper;

	@Autowired
	protected ItemService				itemService;

	@Autowired
	protected AcmeConfigurationService	configService;

	@Autowired
	protected InventorRepository		roleRepo;


	@Autowired
	protected ChimpumService(final ChimpumJpaRepository repo) {
		super(repo);
	}

	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		final ChimpumDto dto = this.mapper.map(entity, ChimpumDto.class);
		request.unbind(entity, model, BindHelper.getAllFieldNames(ChimpumDto.class));

		final String years = MessageHelper.getMessage("general.duration.years", null, "years", request.getLocale());
		final String months = MessageHelper.getMessage("general.duration.months", null, "months", request.getLocale());
		final String days = MessageHelper.getMessage("general.duration.days", null, "days", request.getLocale());
		model.setAttribute("duration", dto.getDuration().replace("$y", " " + years).replace("$m", " " + months).replace("$d", " " + days));

		model.setAttribute("itemId", entity.getItem().getId());
	}

	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		if(request.getModel().getInteger("id")==0) {
			request.bind(entity, errors, BindHelper.getAllFieldNames(NewChimpumDto.class));
			entity.setItem(this.itemService.findById(request.getModel().getInteger("itemId")));
		}else {
			request.bind(entity, errors, BindHelper.getAllFieldNames(SaveChimpumDto.class));
		}
		
		//		
	}

	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		this.configService.checkMoney(request, errors, entity.getBudget(), "budget");
		if (entity.getId() == 0)
			errors.state(request, LocalDate.now().plus(1, ChronoUnit.MONTHS).isBefore(entity.getStartDate().plus(1, ChronoUnit.DAYS)), "startDate", "errors.date.start.ahead.month");
		errors.state(request, entity.getStartDate().plus(1, ChronoUnit.WEEKS).isBefore(entity.getEndDate().plus(1, ChronoUnit.DAYS)), "startDate", "errors.date.end.ahead.week");
	}

	//	public boolean authoriseOwner(final Request<Chimpum> request, final Consumer<Chimpum> setter) {
	//		boolean res = false;
	//		try {
	//			final int id = request.getModel().getInteger("id");
	//			final Chimpum chimpum = this.findById(id);
	//			if (setter != null)
	//				setter.accept(chimpum);
	//			res = chimpum != null && chimpum.getOwner().getUserAccount().getId() == PrincipalHelper.get().getAccountId();
	//		} catch (final Exception e) {
	//			// do nothing
	//			//model get attribute should not have an assert lmao
	//		}
	//		return res;
	//	}

	public boolean authoriseCreate(final Request<Chimpum> request, final Consumer<Item> setter) {
		boolean res = false;
		try {
			final int id = request.getModel().getInteger("itemId");
			final Item item = this.itemService.findById(id);
			if (setter != null)
				setter.accept(item);
			res = item != null && Boolean.TRUE.equals(item.isPublished()) && item.getItemType().equals(Type.COMPONENT) && item.getOwner().getUserAccount().getId() == PrincipalHelper.get().getAccountId();
		} catch (final Exception e) {
			// do nothing
			ChimpumService.logger.error("Error authorising action create on chimpum", e);
		}
		return res;
	}

	public boolean authorise(final Request<Chimpum> request, final Consumer<Chimpum> setter) {
		boolean res = false;
		try {
			final int id = request.getModel().getInteger("id");
			final Chimpum entity = this.findById(id);
			if (setter != null)
				setter.accept(entity);
			res = entity != null && Boolean.TRUE.equals(entity.getItem().isPublished()) && entity.getItem().getItemType().equals(Type.COMPONENT) && entity.getItem().getOwner().getUserAccount().getId() == PrincipalHelper.get().getAccountId();
		} catch (final Exception e) {
			// do nothing
			ChimpumService.logger.error("Error authorising action on chimpum", e);
		}
		return res;
	}

	public Collection<Chimpum> findAllByPrincipalInventor() {
		final Inventor inventor = this.roleRepo.findOneInventorByUserAccountId(PrincipalHelper.get().getAccountId());
		return this.repo.findAll(Specifications.inventorFindAllChimpums(inventor));
	}

	//	@Transactional
	//	public void setPrincipalAsOwner(final Chimpum chimpum) {
	//		final Inventor principal = this.roleRepo.findOneInventorByUserAccountId(PrincipalHelper.get().getAccountId());
	//		chimpum.setOwner(principal);
	//	}
	
//	public String generateCode() {
//		final String res ="";
//		String datePart
//	}
}
