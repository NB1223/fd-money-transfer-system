package com.fd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fd.model.TransactionLog;

@Repository
public interface ITransactionLogRepository extends JpaRepository<TransactionLog, Long>{
	
	// * operator is not recognized by JPA and hence we need this query to be treated as a native query.
	@Query(value = "SELECT * FROM transactionlog WHERE transactionlog.from_account_id = :fromAccountId", nativeQuery = true)
	List<TransactionLog> findTransactionsByAccountId(long fromAccountId);
	
	@Query(value = "SELECT * FROM transactionlog WHERE transactionlog.from_account_id = :accountId OR transactionlog.to_account_id = :accountId", nativeQuery = true)
	Page<TransactionLog> getTransactionsByAccountId(@Param("accountId") long accountId, Pageable pageable);
	
	@Query(value = "SELECT * FROM transactionlog", nativeQuery = true)
	Page<TransactionLog> getAllTransactionsByPage(Pageable pageable);
}
