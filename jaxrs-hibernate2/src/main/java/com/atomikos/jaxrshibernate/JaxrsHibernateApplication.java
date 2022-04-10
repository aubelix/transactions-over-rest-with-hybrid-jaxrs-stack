package com.atomikos.jaxrshibernate;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JaxrsHibernateApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new JaxrsHibernateApplication()
		.configure(new SpringApplicationBuilder(JaxrsHibernateApplication.class))
		.run(args);
	}

}
