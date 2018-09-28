package mywork.task.organizer.dao;

import org.springframework.stereotype.Repository;

import mywork.task.organizer.model.Task;

@Repository
public class EventDao extends AbstractDao<Task, Long> {

	public EventDao() {
		super(Task.class);
	}
}
