package sn.fr.samagp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class SamaGpApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(SamaGpApiApplication.class, args);
	}

}

