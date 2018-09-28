package mywork.task.organizer.util;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

public interface LocalDateUtil {

	static Date convertToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneOffset.systemDefault()).toInstant());
	}
}
