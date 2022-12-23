package com.example.ejercicio101112;

import com.example.ejercicio101112.entity.Laptop;
import com.example.ejercicio101112.repository.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Ejercicio101112Application {

	public static void main(String[] args) {

		ApplicationContext context    = SpringApplication.run(Ejercicio101112Application.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);

		// Load Data
		Laptop lenovo = new Laptop(null, "Lenovo");
		Laptop bgh = new Laptop(null, "BGH");

		repository.save(lenovo);
		repository.save(bgh);
	}

}
