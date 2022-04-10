package com.atomikos.jaxrshibernate.domain;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	private String username;

	Account() {
	}
	
	public Long getId() {
		return id;
	}

	public Account(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

}
