package mywork.task.organizer.service;

import java.time.LocalDate;
import java.util.List;

import mywork.task.organizer.model.Task;
import mywork.task.organizer.model.Visit;
import mywork.task.organizer.model.request.EventRequest;

public interface EventService {
	Task createEvent(EventRequest request);

	Task updateEvent(EventRequest request);

	Task deleteEvent(long id);

	List<Visit> getEventRecords(LocalDate date);

	List<Task> getEvents();
}
