package mywork.task.organizer.model.request;

import java.time.LocalDate;
import java.util.Optional;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import mywork.task.organizer.model.Interval;

@Value.Immutable
@Value.Style(visibility = ImplementationVisibility.PUBLIC)
@JsonDeserialize(builder = ImmutableEventRequest.Builder.class)
public interface TaskRequest {

	Optional<Long> id();

	Optional<String> comment();

	LocalDate from();

	int numOfVisits();

	String location();

	String name();

	Interval type();
}
