
package acme.services.luster;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.Specifications;
import acme.components.util.BindHelper;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.entities.luster.Luster;
import acme.form.luster.LusterDto;
import acme.form.luster.NewLusterDto;
import acme.form.luster.SaveLusterDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.helpers.MessageHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.repositories.InventorRepository;
import acme.repositories.luster.LusterJpaRepository;
import acme.roles.Inventor;
import acme.services.AbstractCrudServiceImpl;
import acme.services.config.AcmeConfigurationService;
import acme.services.item.ItemService;

@Service
@Transactional
public class LusterService extends AbstractCrudServiceImpl<Luster, LusterJpaRepository> {

	private static final Logger			logger	= LoggerFactory.getLogger(LusterService.class);

	@Autowired
	protected ModelMapper				mapper;

	@Autowired
	protected ItemService				itemService;

	@Autowired
	protected AcmeConfigurationService	configService;

	@Autowired
	protected InventorRepository		roleRepo;


	@Autowired
	protected LusterService(final LusterJpaRepository repo) {
		super(repo);
	}

	public void unbind(final Request<Luster> request, final Luster entity, final Model model) {
		final LusterDto dto = this.mapper.map(entity, LusterDto.class);
		request.unbind(entity, model, BindHelper.getAllFieldNames(LusterDto.class));

		final String years = MessageHelper.getMessage("general.duration.years", null, "years", request.getLocale());
		final String months = MessageHelper.getMessage("general.duration.months", null, "months", request.getLocale());
		final String days = MessageHelper.getMessage("general.duration.days", null, "days", request.getLocale());
		model.setAttribute("duration", dto.getDuration().replace("$y", " " + years).replace("$m", " " + months).replace("$d", " " + days));

		model.setAttribute("itemId", entity.getItem().getId());
	}

	public void bind(final Request<Luster> request, final Luster entity, final Errors errors) {
		if (request.getModel().getInteger("id") == 0) {
			request.bind(entity, errors, BindHelper.getAllFieldNames(NewLusterDto.class));
			entity.setItem(this.itemService.findById(request.getModel().getInteger("itemId")));
		} else {
			request.bind(entity, errors, BindHelper.getAllFieldNames(SaveLusterDto.class));
		}

		//		
	}

	public void validate(final Request<Luster> request, final Luster entity, final Errors errors) {
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

	public boolean authoriseCreate(final Request<Luster> request, final Consumer<Item> setter) {
		boolean res = false;
		try {
			final int id = request.getModel().getInteger("itemId");
			final Item item = this.itemService.findById(id);
			if (setter != null)
				setter.accept(item);
			res = this.repo.countByItem_id(id) == 0 && item != null && Boolean.TRUE.equals(item.isPublished()) && item.getItemType().equals(Type.COMPONENT) && item.getOwner().getUserAccount().getId() == PrincipalHelper.get().getAccountId();
		} catch (final Exception e) {
			// do nothing
			LusterService.logger.error("Error authorising action create on chimpum", e);
		}
		return res;
	}

	public boolean authorise(final Request<Luster> request, final Consumer<Luster> setter) {
		boolean res = false;
		try {
			final int id = request.getModel().getInteger("id");
			final Luster entity = this.findById(id);
			if (setter != null)
				setter.accept(entity);
			res = entity != null && Boolean.TRUE.equals(entity.getItem().isPublished()) && entity.getItem().getItemType().equals(Type.COMPONENT) && entity.getItem().getOwner().getUserAccount().getId() == PrincipalHelper.get().getAccountId();
		} catch (final Exception e) {
			// do nothing
			LusterService.logger.error("Error authorising action on chimpum", e);
		}
		return res;
	}

	public Collection<Luster> findAllByPrincipalInventor() {
		final Inventor inventor = this.roleRepo.findOneInventorByUserAccountId(PrincipalHelper.get().getAccountId());
		return this.repo.findAll(Specifications.inventorFindAllChimpums(inventor));
	}

	//	@Transactional
	//	public void setPrincipalAsOwner(final Chimpum chimpum) {
	//		final Inventor principal = this.roleRepo.findOneInventorByUserAccountId(PrincipalHelper.get().getAccountId());
	//		chimpum.setOwner(principal);
	//	}

	public String generateCode() {
		final RandomString random = new RandomString(2, new SecureRandom(), RandomString.alphanum);
		final String localDateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		return String.format("%s%s%s", random.nextString(), localDateStr, random.nextString());
	}


	protected static class RandomString {

		/**
		 * Generate a random string.
		 */
		public String nextString() {
			for (int idx = 0; idx < this.buf.length; ++idx)
				this.buf[idx] = this.symbols[this.random.nextInt(this.symbols.length)];
			return new String(this.buf);
		}


		public static final String	upper		= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		public static final String	lower		= RandomString.upper.toLowerCase(Locale.ROOT);

		public static final String	digits		= "0123456789";

		public static final String	alphanum	= RandomString.upper + RandomString.lower + RandomString.digits;

		private final Random		random;

		private final char[]		symbols;

		private final char[]		buf;


		public RandomString(final int length, final Random random, final String symbols) {
			if (length < 1)
				throw new IllegalArgumentException();
			if (symbols.length() < 2)
				throw new IllegalArgumentException();
			this.random = Objects.requireNonNull(random);
			this.symbols = symbols.toCharArray();
			this.buf = new char[length];
		}

		/**
		 * Create an alphanumeric string generator.
		 */
		public RandomString(final int length, final Random random) {
			this(length, random, RandomString.alphanum);
		}

		/**
		 * Create an alphanumeric strings from a secure generator.
		 */
		public RandomString(final int length) {
			this(length, new SecureRandom());
		}

		/**
		 * Create session identifiers.
		 */
		public RandomString() {
			this(21);
		}

	}
}
