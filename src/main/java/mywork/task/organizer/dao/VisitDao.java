package mywork.task.organizer.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import mywork.task.organizer.model.Visit;

@Repository
public class VisitDao extends AbstractDao<Visit, Long> {

	public VisitDao() {
		super(Visit.class);
	}

	public List<Visit> getVisitsEqualDate(Date date) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Visit> cq = cb.createQuery(Visit.class);
			Root<Visit> from = cq.from(Visit.class);
			cq.select(from);

			Predicate predicate = cb.equal(from.get("day"), date);
			cq.where(cb.and(predicate));

			TypedQuery<Visit> query = entityManager.createQuery(cq);
			return query.getResultList();
		} catch (Exception ex) {
			logger.error("Unable to get today's event records", ex);
			return null;
		}
	}

	public List<Visit> getVisitsAfterDate(Date date) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Visit> cq = cb.createQuery(Visit.class);
			Root<Visit> from = cq.from(Visit.class);
			cq.select(from);

			Predicate predicate = cb.greaterThanOrEqualTo(from.get("day"), date);
			cq.where(cb.and(predicate));

			TypedQuery<Visit> query = entityManager.createQuery(cq);
			return query.getResultList();
		} catch (Exception ex) {
			logger.error("Unable to get today's event records", ex);
			return null;
		}
	}

	// private List<Visit> getVisits(List<Predicate> predicates) {
	// try {
	// CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	// CriteriaQuery<Visit> cq = cb.createQuery(Visit.class);
	// Root<Visit> from = cq.from(Visit.class);
	// cq.select(from);
	//
	// if (!predicates.isEmpty()) {
	// cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
	// }
	// TypedQuery<Visit> query = entityManager.createQuery(cq);
	// return query.getResultList();
	// } catch (Exception ex) {
	// logger.error("Unable to get today's event records", ex);
	// return null;
	// }
	// }
}
