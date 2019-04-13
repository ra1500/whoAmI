package neural;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"main", "neural", "neural.security", "neural.controller", "clientmodel.model", "model", "core", "core.services", "core.transformers", "db", "db.config", "db.entity", "db.repository", "db.repositorytest"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
