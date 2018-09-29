package mywork.task.organizer.dao;

import org.springframework.stereotype.Repository;

import mywork.task.organizer.model.Task;

@Repository
public class TaskDao extends AbstractDao<Task, Long> {

	public TaskDao() {
		super(Task.class);
	}
}
