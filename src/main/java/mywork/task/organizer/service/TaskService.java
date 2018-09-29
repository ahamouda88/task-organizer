package mywork.task.organizer.service;

import java.time.LocalDate;
import java.util.List;

import mywork.task.organizer.model.Task;
import mywork.task.organizer.model.Visit;
import mywork.task.organizer.model.request.TaskRequest;

public interface TaskService {
	Task createTask(TaskRequest request);

	Task updateTask(TaskRequest request);

	Task deleteTask(long id);

	List<Visit> getVisitsEqual(LocalDate date);

	List<Visit> getVisitsEqualOrAfter(LocalDate date);

	List<Task> getTasks();

	List<Task> getDoneVisits();
}
