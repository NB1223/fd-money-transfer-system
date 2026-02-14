package com.fd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fd.model.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long>{
	    Optional<Account> findByHolderName(String holderName);

}
