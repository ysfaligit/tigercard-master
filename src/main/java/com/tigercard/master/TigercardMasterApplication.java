package com.tigercard.master;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TigercardMasterApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TigercardMasterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String s = "2021-05-09T07:00:00+05:30";
	}
}
