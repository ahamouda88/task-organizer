package mywork.task.organizer.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mywork.task.organizer.constant.PathConstants;
import mywork.task.organizer.model.ErrorMessages;
import mywork.task.organizer.model.request.EventRequest;
import mywork.task.organizer.service.EventService;
import mywork.task.organizer.util.ResponseFactoryUtils;

/**
 * This class is a Rest Controller for handling the event & record actions
 */
@RestController
@RequestMapping(value = PathConstants.EVENTS_PATH)
public class EventController {

	@Autowired
	private EventService eventService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createEvent(@RequestBody EventRequest request) {
		if (!isValidRequest(request)) {
			return ResponseFactoryUtils.createFailResponse(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_EVENT_REQUEST);
		}
		if (request.from().isAfter(request.to())) {
			return ResponseFactoryUtils.createFailResponse(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_DATES);
		}
		return ResponseFactoryUtils.createResponse(eventService.createEvent(request), HttpStatus.CREATED,
				HttpStatus.BAD_REQUEST, ErrorMessages.FAILED_CREATE_EVENT);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> updateExpense(@RequestBody EventRequest request) {
		if (!request.id().isPresent()) {
			return ResponseFactoryUtils.createFailResponse(HttpStatus.BAD_REQUEST, ErrorMessages.EMPTY_EVENT_ID);
		}
		if (!isValidRequest(request)) {
			return ResponseFactoryUtils.createFailResponse(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_EVENT_REQUEST);
		}
		if (request.from().isAfter(request.to())) {
			return ResponseFactoryUtils.createFailResponse(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_DATES);
		}
		return ResponseFactoryUtils.createSuccessResponse(eventService.updateEvent(request), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteEvent(@RequestParam("id") Long eventId) {
		return ResponseFactoryUtils.createResponse(eventService.deleteEvent(eventId), HttpStatus.OK,
				HttpStatus.BAD_REQUEST, String.format(ErrorMessages.INVALID_EVENT_ID, eventId));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getEvents() {
		return ResponseFactoryUtils.createResponse(eventService.getEvents(), HttpStatus.OK, HttpStatus.BAD_REQUEST,
				ErrorMessages.FAILED_GET_ALL_EVENTS);
	}

	@RequestMapping(value = PathConstants.RECORDS_PATH, method = RequestMethod.GET)
	public ResponseEntity<?> getRecords(@RequestParam(required = false, value = "date") LocalDate date) {
		if (date == null)
			date = LocalDate.now();

		return ResponseFactoryUtils.createResponse(eventService.getEventRecords(date), HttpStatus.OK,
				HttpStatus.BAD_REQUEST, ErrorMessages.FAILED_GET_ALL_RECORDS);
	}

	private boolean isValidRequest(EventRequest request) {
		return !(StringUtils.isEmpty(request.description()) || request.from() == null || request.to() == null
				|| request.type() == null);
	}
}
