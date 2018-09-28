package mywork.task.organizer.service;

import static mywork.task.organizer.util.LocalDateUtil.convertToDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mywork.task.organizer.dao.EventDao;
import mywork.task.organizer.dao.EventRecordDao;
import mywork.task.organizer.model.Task;
import mywork.task.organizer.model.Visit;
import mywork.task.organizer.model.Interval;
import mywork.task.organizer.model.request.EventRequest;

@Service
@Transactional
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDao eventDao;

	@Autowired
	private EventRecordDao recordDao;

	@Override
	public Task createEvent(EventRequest request) {
		return eventDao.create(mapToEvent(request));
	}

	@Override
	public Task updateEvent(EventRequest request) {
		long id = request.id().get();
		Task oldEvent = eventDao.find(id);
		Task newEvent = mapToEvent(request);
		newEvent.setId(id);
		return eventDao.update(newEvent);
	}

	@Override
	public Task deleteEvent(long id) {
		return eventDao.remove(id);
	}

	@Override
	public List<Visit> getEventRecords(LocalDate date) {
		return recordDao.getRecordsByDate(convertToDate(date));
	}

	@Override
	public List<Task> getEvents() {
		return eventDao.getAll();
	}

	public static Task mapToEvent(EventRequest request) {
		LocalDate from = request.from();
		LocalDate to = request.to();
		Task event = new Task(request.description(), convertToDate(from), convertToDate(to), request.type());
		List<Visit> records = new ArrayList<>();
		while (from.isBefore(to) || from.isEqual(to)) {
			records.add(new Visit(convertToDate(from), event));
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
		}
		event.setRecords(records);
		return event;
	}
}
