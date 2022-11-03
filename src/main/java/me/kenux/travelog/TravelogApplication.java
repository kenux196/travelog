package me.kenux.travelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TravelogApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelogApplication.class, args);
	}

}
