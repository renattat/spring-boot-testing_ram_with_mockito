package ru.learningproject.spring_boot_testing_ram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTestingRamApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestingRamApplication.class, args);

		Student student =  new Student(1, "Renat");
		System.out.println(student.getId());
		System.out.println(student.getName());
	}

}
