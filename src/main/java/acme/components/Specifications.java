
package acme.components;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import acme.entities.chimpum.Chimpum;
import acme.entities.item.Item;
import acme.entities.patronage.Patronage;
import acme.entities.patronage.Patronage.Status;
import acme.entities.patronagereport.PatronageReport;
import acme.entities.toolkit.Toolkit;
import acme.entities.toolkititem.ToolkitItem;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.roles.Administrator;
import acme.framework.roles.UserRole;
import acme.roles.Inventor;

public class Specifications {

	private Specifications() {
		throw new IllegalStateException("Utility class");
	}

	public static Specification<UserAccount> userAccountIsEnabledAndNotSystem() {
		return new Specification<UserAccount>() {

			private static final long serialVersionUID = 1L;


			@Override
			public Predicate toPredicate(final Root<UserAccount> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				final Fetch<UserAccount, UserRole> fetchRoles = root.fetch("roles", JoinType.LEFT);
				return cb.and(cb.isTrue(root.get("enabled")), cb.not(cb.literal(Administrator.class).in(fetchRoles)));
			}

		};
	}

	// ######################## ITEMS

	public static Specification<Item> itemIsOfType(final Item.Type itemType, final Boolean published) {
		return new Specification<Item>() {

			private static final long serialVersionUID = 2L;


			@Override
			public Predicate toPredicate(final Root<Item> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				return cb.and(cb.equal(root.get("itemType"), itemType), cb.equal(root.get("published"), published != null ? cb.literal(published) : cb.and()));
			}

		};
	}

	public static Specification<Item> itemIsOfTypeAndPrincipalIsOwner(final Item.Type itemType) {
		return new Specification<Item>() {

			private static final long serialVersionUID = 3L;


			@Override
			public Predicate toPredicate(final Root<Item> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				final Principal principal = PrincipalHelper.get();
				root.fetch("owner", JoinType.INNER).fetch("userAccount", JoinType.INNER);
				return cb.and(cb.equal(root.get("itemType"), itemType), cb.equal(root.get("owner").get("userAccount").get("username"), cb.literal(principal.getUsername())));
			}

		};
	}

	public static Specification<Item> itemIsOfTypeByToolkit(final Item.Type itemType, final Toolkit kit, final Boolean published) {
		return new Specification<Item>() {

			private static final long serialVersionUID = 33L;


			@Override
			public Predicate toPredicate(final Root<Item> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				final Join<Item, ToolkitItem> joinToolkitItem = root.join("toolkits", JoinType.LEFT);
				return cb.and(cb.equal(root.get("itemType"), itemType), cb.equal(joinToolkitItem.get("toolkit"), kit), cb.equal(root.get("published"), published != null ? cb.literal(published) : cb.and()));
			}

		};
	}

	public static Specification<Item> itemOfTypeIsPublishedAndNotIn(final Item.Type itemType, final List<Integer> itemIds) {
		return new Specification<Item>() {

			private static final long serialVersionUID = 33L;


			@Override
			public Predicate toPredicate(final Root<Item> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				return cb.and(cb.equal(root.get("itemType"), itemType), cb.not(root.get("id").in(itemIds)), cb.isFalse(root.get("published")));
			}

		};
	}

	// ######################## TOOLKITS

	public static Specification<Toolkit> toolkitHasItem(final Item item, final Boolean published) {
		return new Specification<Toolkit>() {

			private static final long serialVersionUID = 4L;


			@Override
			public Predicate toPredicate(final Root<Toolkit> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				final Join<Toolkit, ToolkitItem> joinToolkitItem = root.join("items", JoinType.LEFT);
				final Join<ToolkitItem, Item> joinItem = joinToolkitItem.join("item", JoinType.LEFT);
				return cb.and(cb.literal(item).in(joinItem), cb.equal(root.get("published"), published != null ? cb.literal(published) : cb.and()));
			}
		};
	}

	public static Specification<Toolkit> toolkitPrincipalIsOwner() {
		return new Specification<Toolkit>() {

			private static final long serialVersionUID = 3L;


			@Override
			public Predicate toPredicate(final Root<Toolkit> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				final Principal principal = PrincipalHelper.get();
				root.fetch("owner", JoinType.INNER).fetch("userAccount", JoinType.INNER);
				return cb.equal(root.get("owner").get("userAccount").get("username"), cb.literal(principal.getUsername()));
			}

		};
	}

	// ######################## PATRONAGE

	//must be published also
	public static Specification<Patronage> patronagePrincipalIsSponsee() {
		return new Specification<Patronage>() {

			private static final long serialVersionUID = 44L;


			@Override
			public Predicate toPredicate(final Root<Patronage> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				final Principal principal = PrincipalHelper.get();
				root.fetch("sponsee", JoinType.INNER).fetch("userAccount", JoinType.INNER);
				return cb.and(cb.equal(root.get("sponsee").get("userAccount").get("username"), cb.literal(principal.getUsername())), cb.notEqual(root.get("status"), Status.UNLISTED));
			}

		};
	}

	//all
	public static Specification<Patronage> patronagePrincipalIsSponsor() {
		return new Specification<Patronage>() {

			private static final long serialVersionUID = 4L;


			@Override
			public Predicate toPredicate(final Root<Patronage> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				final Principal principal = PrincipalHelper.get();
				root.fetch("sponsor", JoinType.INNER).fetch("userAccount", JoinType.INNER);
				return cb.equal(root.get("sponsor").get("userAccount").get("username"), cb.literal(principal.getUsername()));
			}

		};
	}

	// ######################## PATRONAGE REPORT

	public static Specification<PatronageReport> patronageReportByPatronage(final Patronage patronage) {
		return new Specification<PatronageReport>() {

			private static final long serialVersionUID = 55L;


			@Override
			public Predicate toPredicate(final Root<PatronageReport> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				root.fetch("patronage", JoinType.LEFT);
				return cb.equal(root.get("patronage"), cb.literal(patronage));
			}
		};
	}
	
	// ######################## CHIMPUM
	
	public static Specification<Chimpum> inventorFindAllChimpums(final Inventor inventor) {
		return new Specification<Chimpum>() {

			private static final long serialVersionUID = 69L;


			@Override
			public Predicate toPredicate(final Root<Chimpum> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				final Join<Chimpum,Item> chimpumItem = root.join("item", JoinType.INNER);
				return cb.equal(chimpumItem.get("owner"), inventor);
			}
		};
	}

}
