package com.atomikos.jaxrshibernateclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.atomikos.jaxrshibernateclient.service.PaymentService;

@SpringBootApplication
public class SampleRestClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SampleRestClientApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);
    }

    @Autowired
    PaymentService paymentService;
    @Bean
    CommandLineRunner initWebClientRunner() {

        return new CommandLineRunner() {

            @Override
            public void run(String... runArgs) throws Exception {
            	//will perform a commit
            	paymentService.savePaymentAndAccount("demo1", 10, "unique");
            	System.out.println("-----------------------------------------------------");
            	//will perform a rollback
            	//paymentService.savePaymentAndAccount("demo2", 22, "unique");
            }
        };
    }

}
