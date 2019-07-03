package digital.formica.training.elastic.brewery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BrewerySearchApplication {
	
	public static void main(String[] args) {
		System.setProperty("jsse.enableSNIExtension", "false");
		SpringApplication.run(BrewerySearchApplication.class, args);
	}

}
