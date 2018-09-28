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

	@RequestMapping(value = "/view/event", method = RequestMethod.GET)
	public String eventPage() {
		return PageConstants.EVENT_PAGE;
	}

	@RequestMapping(value = "/view/events", method = RequestMethod.GET)
	public String eventsPage() {
		return PageConstants.EVENTS_PAGE;
	}

	@RequestMapping(value = "/view/records", method = RequestMethod.GET)
	public String recordsPage() {
		return PageConstants.RECORDS_PAGE;
	}
}
