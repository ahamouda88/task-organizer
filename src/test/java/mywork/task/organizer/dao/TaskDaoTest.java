//package mywork.task.organizer.dao;
//
//import static mywork.task.organizer.util.LocalDateUtil.convertToDate;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import mywork.task.organizer.SpringBootConfig;
//import mywork.task.organizer.model.Task;
//import mywork.task.organizer.model.Visit;
//import mywork.task.organizer.model.Interval;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@SpringBootTest(classes = { SpringBootConfig.class, TaskDao.class, VisitDao.class })
//@EntityScan(basePackages = "mywork.task.organizer.dao")
//public class TaskDaoTest {
//
//	@Autowired
//	private TaskDao eventDao;
//
//	@Autowired
//	private VisitDao recordDao;
//
//	private long recordId;
//
//	@Before
//	public void testCreateAndGetEvent() {
//		String description = "Test Description";
//		Interval type = Interval.DAILY;
//		LocalDate from = LocalDate.of(2018, 8, 8);
//		LocalDate to = LocalDate.of(2018, 8, 14);
//		Task event = createEvent(description, from, to, type);
//		eventDao.create(event);
//
//		List<Task> events = eventDao.getAll();
//		assertEquals(1, events.size());
//
//		event = events.get(0);
//		recordId = event.getId();
//		assertEquals(description, event.getDescription());
//		assertEquals(type, event.getType());
//		assertNotNull(event.getStart());
//		assertNotNull(event.getEnd());
//
//		List<Visit> records = event.getRecords();
//		assertEquals(7, records.size());
//	}
//
//	@Test
//	public void testUpdateEvent() {
//		String newDescription = "New Description";
//		Task event = eventDao.find(recordId);
//		event.setDescription(newDescription);
//		eventDao.update(event);
//
//		event = eventDao.find(recordId);
//		assertEquals(newDescription, event.getDescription());
//	}
//
//	@Test
//	public void testRemoveEvent() {
//		List<Visit> records = recordDao.getAll();
//		assertEquals(7, records.size());
//
//		eventDao.remove(recordId);
//
//		List<Task> events = eventDao.getAll();
//		records = recordDao.getAll();
//		assertEquals(0, events.size());
//		assertEquals(0, records.size());
//	}
//
//	@Test
//	public void testGetRecordsByDate() {
//		LocalDate date = LocalDate.of(2018, 8, 9);
//		List<Visit> records = recordDao.getRecordsByDate(convertToDate(date));
//		assertEquals(1, records.size());
//	}
//
//	private Visit createRecord(Task event, Date date) {
//		return new Visit(date, event);
//	}
//
//	private Task createEvent(String desc, LocalDate from, LocalDate to, Interval type) {
//		Date fromDate = convertToDate(from);
//		Date toDate = convertToDate(to);
//		Task event = new Task(desc, fromDate, toDate, type);
//
//		List<Visit> records = new ArrayList<>();
//		while (from.isBefore(to) || from.isEqual(to)) {
//			records.add(createRecord(event, convertToDate(from)));
//			switch (type) {
//			case DAILY:
//				from = from.plusDays(1);
//				break;
//			case WEEKLY:
//				from = from.plusWeeks(1);
//				break;
//			default:
//				from = from.plusMonths(1);
//			}
//		}
//		event.setRecords(records);
//		return event;
//	}
//}