package mywork.task.organizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * This Spring Configuration class, is used for starting the application using
 * Spring Boot
 */
@SpringBootApplication(scanBasePackages = { "mywork.task.organizer" })
@EntityScan(basePackages = "mywork.task.organizer.model")
public class SpringBootConfig extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(new Class[] { SpringBootConfig.class, WebConfig.class }, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootConfig.class);
	}
}
