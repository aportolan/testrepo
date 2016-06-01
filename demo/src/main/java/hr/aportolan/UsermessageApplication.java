package hr.aportolan;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableJSONDoc
public class UsermessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermessageApplication.class, args);
	}
}
