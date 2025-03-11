package com.mdotm.pets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class PetsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetsManagementApplication.class, args);
	}

}
