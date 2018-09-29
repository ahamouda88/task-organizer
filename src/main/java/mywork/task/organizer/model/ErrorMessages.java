package mywork.task.organizer.model;

public interface ErrorMessages {

	String FAILED_CREATE_TASK = "Failed to create task";
	String FAILED_UPDATE_TASK = "Failed to update task";
	String FAILED_GET_REMAINING_VISITS = "Failed to get ramining visits";
	String FAILED_GET_DONE_VISITS = "Failed to get done visits";

	String INVALID_TASK_ID = "Task with id [%d], doesn't exist";
	String INVALID_TASK_REQUEST = "Event request is invalid";
	String INVALID_START_DATE= "Start date should be after today's date";
	String EMPTY_TASK_ID = "Event id should not be empty";
}
