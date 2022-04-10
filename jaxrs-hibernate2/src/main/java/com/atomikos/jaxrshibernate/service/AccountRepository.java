package com.atomikos.jaxrshibernate.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atomikos.jaxrshibernate.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}

