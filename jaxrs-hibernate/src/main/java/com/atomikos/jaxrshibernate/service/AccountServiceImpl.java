package com.atomikos.jaxrshibernate.service;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atomikos.jaxrshibernate.domain.Account;

import sample.rs.service.api.HelloService;

@Path("/account")
@Service
@Transactional
public class AccountServiceImpl implements HelloService {



	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TestServiceImpl testService;

	@Override
	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello(@PathParam("name")  String name) {
		return "Hello2 " + name + ", Welcome to CXF RS Spring Boot World!!!";
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(Account account) {
		Account savedAccount = accountRepository.save(account);
		System.out.println(savedAccount.getId());


		// here we start a new transaction by @Transactional(value = TxType.REQUIRES_NEW)
		// this will lead to an error in com.atomikos.icatch.imp.ActiveStateHandler.orphansExist()
		testService.doTest();

		return Response.ok(savedAccount).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAccount(Account account) {
		accountRepository.save(account);
		return Response.accepted().build();
	}

}