package lbs.lbs;

import lbs.lbs.controller.BoardController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LbsApplication {

	public static void main(String[] args) {
		System.out.println("테스트1");
		SpringApplication.run(LbsApplication.class, args);
	}
}