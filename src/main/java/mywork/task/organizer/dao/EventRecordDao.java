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
public class EventRecordDao extends AbstractDao<Visit, Long> {

	public EventRecordDao() {
		super(Visit.class);
	}

	public List<Visit> getRecordsByDate(Date date) {
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
}
