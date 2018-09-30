package mywork.task.organizer.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
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

	public void deleteVisitsByTaskId(long taskId) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();

			CriteriaDelete<Visit> delete = cb.createCriteriaDelete(Visit.class);

			Root<Visit> from = delete.from(Visit.class);
			// set where clause
			delete.where(cb.equal(from.get("task"), taskId));

			// perform update
			this.entityManager.createQuery(delete).executeUpdate();
		} catch (Exception ex) {
			logger.error("Unable to delete visits of task Id: " + taskId, ex);
		}
	}

	public List<Visit> getVisitsEqualDate(Date date) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Visit> cq = cb.createQuery(Visit.class);
			Root<Visit> from = cq.from(Visit.class);
			cq.select(from);

			Predicate predicate = cb.equal(from.get("day"), date);
			cq.where(cb.and(predicate));

			return entityManager.createQuery(cq).getResultList();
		} catch (Exception ex) {
			logger.error("Unable to get visits", ex);
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

			return entityManager.createQuery(cq).getResultList();
		} catch (Exception ex) {
			logger.error("Unable to get visits", ex);
			return null;
		}
	}
}
