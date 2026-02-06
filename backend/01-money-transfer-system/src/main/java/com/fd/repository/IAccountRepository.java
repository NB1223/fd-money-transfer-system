package com.fd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fd.model.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long>{
	
}
