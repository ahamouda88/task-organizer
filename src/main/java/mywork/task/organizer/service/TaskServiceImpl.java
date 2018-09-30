package mywork.task.organizer.service;

import static mywork.task.organizer.util.LocalDateUtil.convertToDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import mywork.task.organizer.dao.TaskDao;
import mywork.task.organizer.dao.VisitDao;
import mywork.task.organizer.model.Task;
import mywork.task.organizer.model.Visit;
import mywork.task.organizer.model.request.TaskRequest;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private VisitDao visitDao;

	@Override
	public Task createTask(TaskRequest request) {
		return taskDao.create(mapToTask(request));
	}

	@Override
	public Task updateTask(TaskRequest request) {
		long id = request.id().get();
		visitDao.deleteVisitsByTaskId(id);

		Task oldTask = taskDao.find(id);
		Task newTask = mapToTask(request);
		oldTask.setLocation(newTask.getLocation());
		oldTask.setName(newTask.getName());
		oldTask.setVisits(newTask.getVisits());
		oldTask.setComment(newTask.getComment());
		oldTask.setStart(newTask.getStart());
		oldTask.setType(newTask.getType());
		oldTask.setNumOfVisits(request.numOfVisits());
		return taskDao.update(oldTask);
	}

	@Override
	public Task deleteTask(long id) {
		return taskDao.remove(id);
	}

	@Override
	public List<Visit> getVisitsEqual(LocalDate date) {
		List<Visit> visits = visitDao.getVisitsEqualDate(convertToDate(date));
		sortVisits(visits);
		return visits;
	}

	// This method will be used to get remaining visits
	@Override
	public List<Visit> getVisitsEqualOrAfter(LocalDate date) {
		List<Visit> visits = visitDao.getVisitsAfterDate(convertToDate(date));
		sortVisits(visits);
		return visits;
	}

	@Override
	public List<Task> getTasks() {
		return taskDao.getAll();
	}

	@Override
	public List<Task> getDoneTasks() {
		List<Task> tasks = getTasks();
		if (CollectionUtils.isEmpty(tasks))
			return new ArrayList<>();

		Date now = Calendar.getInstance().getTime();
		return tasks.stream().filter(task -> !task.getVisits().stream().map(Visit::getDay)
				.anyMatch(day -> day.after(now) || day.equals(now))).collect(Collectors.toList());
	}

	public static Task mapToTask(TaskRequest request) {
		LocalDate from = request.from();
		Task task = new Task(request.numOfVisits(), request.comment().orElse(""), request.location(), request.name(),
				request.type(), convertToDate(from));
		List<Visit> visits = new ArrayList<>();
		int numOfVisits = request.numOfVisits();
		while (numOfVisits > 0) {
			visits.add(new Visit(convertToDate(from), task));
			switch (request.type()) {
			case DAILY:
				from = from.plusDays(1);
				break;
			case WEEKLY:
				from = from.plusWeeks(1);
				break;
			default:
				from = from.plusMonths(1);
			}
			numOfVisits--;
		}
		task.setVisits(visits);
		return task;
	}

	private void sortVisits(List<Visit> visits) {
		if (CollectionUtils.isEmpty(visits))
			return;

		visits.sort((v1, v2) -> v1.getDay().compareTo(v2.getDay()));
	}
}
