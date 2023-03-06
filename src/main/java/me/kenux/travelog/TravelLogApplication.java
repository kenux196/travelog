package me.kenux.travelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@SpringBootApplication
public class TravelLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelLogApplication.class, args);
	}

}
