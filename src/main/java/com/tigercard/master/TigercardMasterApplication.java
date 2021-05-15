package com.tigercard.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tigercard.master.entity.Trip;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TigercardMasterApplication implements CommandLineRunner {

	ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) {
		SpringApplication.run(TigercardMasterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
