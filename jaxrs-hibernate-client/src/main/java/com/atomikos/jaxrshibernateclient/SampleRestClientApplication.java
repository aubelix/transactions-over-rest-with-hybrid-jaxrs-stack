package com.atomikos.jaxrshibernateclient;

import static javax.ws.rs.client.ClientBuilder.newClient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.atomikos.jaxrshibernateclient.service.PaymentService;
import com.atomikos.remoting.jaxrs.TransactionAwareRestClientFilter;
import com.atomikos.remoting.taas.TransactionProvider;
import com.atomikos.remoting.twopc.ParticipantsProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@SpringBootApplication
public class SampleRestClientApplication {
    public static void main(String[] args) {
    	System.setProperty("com.atomikos.icatch.max_timeout", "300000");
    	System.setProperty("com.atomikos.icatch.default_jta_timeout", "300000");
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
            	
            	paymentService.testTransitiveReadOnlyCalls();
            	
            	
            	
            	//will perform a commit
            	paymentService.savePaymentAndAccount("demo1", 10, "unique");
            	System.out.println("-----------------------------------------------------");
            	//will perform a rollback
            	//paymentService.savePaymentAndAccount("demo2", 22, "unique");
            }
        };
    }

}
