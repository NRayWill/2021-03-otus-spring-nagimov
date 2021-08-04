package ru.otus.spring.rnagimov.libraryorm;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class LibraryOrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryOrmApplication.class);
		// Console.main(args);
	}
}
