package com.ajith.security;

import com.ajith.security.admin.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication implements CommandLineRunner {

	@Autowired
	private IAdminService iAdminService;

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	/**
	 * Callback used to run the bean.
	 *
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	@Override
	public void run (String... args) throws Exception {
		iAdminService.createAdmin();
	}
}
