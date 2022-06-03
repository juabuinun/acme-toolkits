package acme.repositories.luster;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import acme.datatypes.CustomMoney;
import acme.entities.luster.Luster;

@Repository
@Transactional(readOnly = true)
public class LusterAdvancedRepositoryImpl implements LusterAdvancedRepository{

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public List<CustomMoney> findMaxBudget() {
		final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<CustomMoney> query = cb.createQuery(CustomMoney.class);
		final Root<Luster> root = query.from(Luster.class);

		final Expression<Double> expression = cb.max(root.get("budget").get("amount"));

//		final Join<Patronage,Patron> patronagePatron = root.join("sponsor", JoinType.INNER);
//		final Join<Patron,UserAccount> patronUser = patronagePatron.join("userAccount", JoinType.INNER);
//		final Principal principal = PrincipalHelper.get();

		query.select(cb.construct(CustomMoney.class, expression, root.get("budget").get("currency")));
//		query.where(cb.and(cb.equal(root.get("status"), cb.literal(status)), cb.equal(patronUser.get("id"), cb.literal(principal.getAccountId()))));
		query.groupBy(root.get("budget").get("currency"));
		query.orderBy(cb.desc(expression));
		return this.entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<CustomMoney> findMinBudget() {
		final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<CustomMoney> query = cb.createQuery(CustomMoney.class);
		final Root<Luster> root = query.from(Luster.class);
		final Expression<Double> expression = cb.min(root.get("budget").get("amount"));

//		final Principal principal = PrincipalHelper.get();
//		
//		final Join<Patronage,Patron> patronagePatron = root.join("sponsor", JoinType.INNER);
//		final Join<Patron,UserAccount> patronUser = patronagePatron.join("userAccount", JoinType.INNER);

		query.select(cb.construct(CustomMoney.class, expression, root.get("budget").get("currency")));
//		query.where(cb.and(cb.equal(root.get("status"), cb.literal(status)), cb.equal(patronUser.get("id"), cb.literal(principal.getAccountId()))));
		query.groupBy(root.get("budget").get("currency"));
		query.orderBy(cb.desc(expression));
		return this.entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<CustomMoney> findAvgBudget() {
		final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<CustomMoney> query = cb.createQuery(CustomMoney.class);
		final Root<Luster> root = query.from(Luster.class);

//		final Join<Patronage,Patron> patronagePatron = root.join("sponsor", JoinType.INNER);
//		final Join<Patron,UserAccount> patronUser = patronagePatron.join("userAccount", JoinType.INNER);
//		final Principal principal = PrincipalHelper.get();

		final Expression<Double> expression = cb.avg(root.get("budget").get("amount"));

		query.select(cb.construct(CustomMoney.class, expression, root.get("budget").get("currency")));
//		query.where(cb.and(cb.equal(root.get("status"), cb.literal(status)), cb.equal(patronUser.get("id"), cb.literal(principal.getAccountId()))));
		query.groupBy(root.get("budget").get("currency"));
		query.orderBy(cb.desc(expression));
		return this.entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<CustomMoney> findStdevBudget() {
	
		return this.findAvgBudget().stream().map(avg -> {
			final CustomMoney res = new CustomMoney();
			res.setCurrency(avg.getCurrency());
			final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
			final CriteriaQuery<Double> query = cb.createQuery(Double.class);
			final Root<Luster> root = query.from(Luster.class);
			final Expression<Double> diffWithAvg = cb.diff(root.get("budget").get("amount"), avg.getAmount());

//			final Join<Patronage,Patron> patronagePatron = root.join("sponsor", JoinType.INNER);
//			final Join<Patron,UserAccount> patronUser = patronagePatron.join("userAccount", JoinType.INNER);
			

			query.select(cb.sum(cb.prod(diffWithAvg, diffWithAvg)));
			query.where(cb.equal(root.get("budget").get("currency"), avg.getCurrency()));

			final Double sum = this.entityManager.createQuery(query).getSingleResult();

			final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
			final Root<Luster> rootCount = countQuery.from(Luster.class);
			
			
			countQuery.select(cb.count(rootCount));
			countQuery.where(cb.equal(rootCount.get("budget").get("currency"), avg.getCurrency()));

			final Long count = this.entityManager.createQuery(countQuery).getSingleResult();

			res.setAmount(Math.sqrt(sum / count));
			return res;
		}).collect(Collectors.toList());
	}

	
}
