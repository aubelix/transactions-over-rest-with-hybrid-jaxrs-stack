package com.atomikos.jaxrshibernate.service;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestServiceImpl {

	@Transactional(value = TxType.REQUIRES_NEW)
	public void doTest() {


	}

}
