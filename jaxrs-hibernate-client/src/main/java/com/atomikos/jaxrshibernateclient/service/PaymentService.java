package com.atomikos.jaxrshibernateclient.service;

import static javax.ws.rs.client.ClientBuilder.newClient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atomikos.jaxrshibernate.domain.Account;
import com.atomikos.remoting.jaxrs.TransactionAwareRestClientFilter;
import com.atomikos.remoting.taas.TransactionProvider;
import com.atomikos.remoting.twopc.ParticipantsProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Service
@Transactional
public class PaymentService {
	@Autowired
	PaymentRepository paymentRepository;
	WebTarget target;
	public PaymentService() {
		Client client = newClient().register(TransactionProvider.class).register(JacksonJsonProvider.class).register(ParticipantsProvider.class).register(TransactionAwareRestClientFilter.class);
		target = client.target("http://localhost:8080/transactions/");
		
	}
	
	public void savePaymentAndAccount(String name, Integer amount, String unique) {
		//Perform DB work
		
		Payment payment = new Payment(name, amount, unique);
		paymentRepository.save(payment);
		
		//call rest endpoint
		Account account = new Account(name);
		Account invoke = target.path("/account").request().accept(MediaType.APPLICATION_JSON).buildPost(Entity.entity(account, MediaType.APPLICATION_JSON)).invoke(Account.class);
		System.out.println(invoke.getId());
		
		if ("error".equals(name)) {
			throw new RuntimeException("Simulated error");
		}
	}
	
	
	
	/**
	 * Testcase for error in ActiveStateHandler.prepare()
	 * 
	 * <pre>
	 *  
	 * 	if ( allReadOnly ) {
     *       nextStateHandler = new TerminatedStateHandler ( this );
     *       getCoordinator ().setStateHandler ( nextStateHandler );
	 * </pre>
	 * 
	 * If readOnly calls are done from Services 1 and Service 2 to  Service 3 we get an error in the prepare phase.
	 * Service 1 wants to do the prepare and because of the 'allReadOnly' logic in ActiveStateHandler the Coordinator is removed 
	 * in TransactionServiceImp.removeCoordinator(). When Service 2 wants to do prepare on Service 3, the Coordinator is missing and  
	 * TransactionServiceImp.getCoordinatorImpForRoot() will not find the coordinator.
	 */
	public void testTransitiveReadOnlyCalls() {

    	// Call the readOnly sayHello operation on server 8081
		Client client = newClient().register(TransactionProvider.class).register(JacksonJsonProvider.class).register(ParticipantsProvider.class).register(TransactionAwareRestClientFilter.class);
		WebTarget target = client.target("http://localhost:8081/transactions/account");
		target.path("/test").request().accept(MediaType.TEXT_PLAIN).buildGet().invoke();

		//then call the readOnly sayHello operation on server 8080
		// sayHello on 8080 will also do a call to sayHello in 8081
		// this means, sayHello on 8081 ist called twice
		target = client.target("http://localhost:8080/transactions/account");
		target.path("/test").request().accept(MediaType.TEXT_PLAIN).buildGet().invoke();

		
	}
}
