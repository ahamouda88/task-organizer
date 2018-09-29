package mywork.task.organizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mywork.task.organizer.constant.PageConstants;

@Controller
public class SpringAngularController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String appPage() {
		return PageConstants.APP_PAGE;
	}

	@RequestMapping(value = "/view/task", method = RequestMethod.GET)
	public String eventPage() {
		return PageConstants.TASK_PAGE;
	}

	@RequestMapping(value = "/view/remainingVisits", method = RequestMethod.GET)
	public String eventsPage() {
		return PageConstants.REMAINING_VISITS_PAGE;
	}

	@RequestMapping(value = "/view/doneVisits", method = RequestMethod.GET)
	public String recordsPage() {
		return PageConstants.DONE_VISITS_PAGE;
	}
}
