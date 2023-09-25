package com.imaginnovate.websecuritycheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

@SpringBootApplication
public class WebsecuritycheckApplication {

	public static void main(String[] args) {


		SpringApplication.run(WebsecuritycheckApplication.class, args);
	}

}
