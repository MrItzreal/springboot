package com.codewithizzy.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		// 'ctx' short for context
		var ctx = SpringApplication.run(SpringbootApplication.class, args);

		MyFirstService myFirstService = ctx.getBean(MyFirstService.class);
		System.out.println(myFirstService.tellAStory());
		System.out.println(myFirstService.getCustomProperty());
		System.out.println(myFirstService.getCustomPropertyInt());
		System.out.println(myFirstService.getCustomPropertyFromAnotherFile());
		System.out.println(myFirstService.getCustomPropertyFromAnotherFile2());
	}
}