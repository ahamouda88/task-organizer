package mywork.task.organizer.model;

public interface ErrorMessages {

	String FAILED_CREATE_EVENT = "Failed to create event";
	String FAILED_UPDATE_EVENT = "Failed to update event";
	String FAILED_GET_ALL_EVENTS = "Failed to get all events";
	String FAILED_GET_ALL_RECORDS = "Failed to get all records";

	String INVALID_EVENT_ID = "Event with id [%d], doesn't exist";
	String INVALID_EVENT_REQUEST = "Event request is invalid";
	String INVALID_DATES = "From date should be before To Date";
	String EMPTY_EVENT_ID = "Event id should not be empty";
}
