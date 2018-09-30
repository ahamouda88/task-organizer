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
import mywork.task.organizer.model.request.TaskRequest;
import mywork.task.organizer.service.TaskService;
import mywork.task.organizer.util.ResponseFactoryUtils;

/**
 * This class is a Rest Controller for handling the task & record actions
 */
@RestController
@RequestMapping(value = PathConstants.TASKS_PATH)
public class TaskController {

	@Autowired
	private TaskService taskService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createTask(@RequestBody TaskRequest request) {
		if (!isValidRequest(request)) {
			return ResponseFactoryUtils.createFailResponse(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_TASK_REQUEST);
		}
		if (request.from().isBefore(LocalDate.now().minusDays(1))) {
			return ResponseFactoryUtils.createFailResponse(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_START_DATE);
		}
		return ResponseFactoryUtils.createResponse(taskService.createTask(request), HttpStatus.CREATED,
				HttpStatus.BAD_REQUEST, ErrorMessages.FAILED_CREATE_TASK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> updateExpense(@RequestBody TaskRequest request) {
		if (!request.id().isPresent()) {
			return ResponseFactoryUtils.createFailResponse(HttpStatus.BAD_REQUEST, ErrorMessages.EMPTY_TASK_ID);
		}
		if (!isValidRequest(request)) {
			return ResponseFactoryUtils.createFailResponse(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_TASK_REQUEST);
		}
		if (request.from().isBefore(LocalDate.now().minusDays(1))) {
			return ResponseFactoryUtils.createFailResponse(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_START_DATE);
		}
		return ResponseFactoryUtils.createSuccessResponse(taskService.updateTask(request), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTask(@RequestParam("id") Long taskId) {
		return ResponseFactoryUtils.createResponse(taskService.deleteTask(taskId), HttpStatus.OK,
				HttpStatus.BAD_REQUEST, String.format(ErrorMessages.INVALID_TASK_ID, taskId));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllTasks() {
		return ResponseFactoryUtils.createResponse(taskService.getTasks(), HttpStatus.OK, HttpStatus.BAD_REQUEST,
				ErrorMessages.FAILED_GET_ALL_TASKS);
	}

	@RequestMapping(value = PathConstants.DONE_TASKS_PATH, method = RequestMethod.GET)
	public ResponseEntity<?> getDoneTasks() {
		return ResponseFactoryUtils.createResponse(taskService.getDoneTasks(), HttpStatus.OK, HttpStatus.BAD_REQUEST,
				ErrorMessages.FAILED_GET_DONE_TASKS);
	}

	@RequestMapping(value = PathConstants.REMAINING_VISITS_PATH, method = RequestMethod.GET)
	public ResponseEntity<?> getRemainingVisits(@RequestParam(required = false, value = "date") LocalDate date) {
		if (date == null)
			date = LocalDate.now();

		return ResponseFactoryUtils.createResponse(taskService.getVisitsEqualOrAfter(date), HttpStatus.OK,
				HttpStatus.BAD_REQUEST, ErrorMessages.FAILED_GET_REMAINING_VISITS);
	}

	private boolean isValidRequest(TaskRequest request) {
		return !(StringUtils.isEmpty(request.location()) || StringUtils.isEmpty(request.name())
				|| request.from() == null || request.type() == null || request.numOfVisits() < 1);
	}
}
