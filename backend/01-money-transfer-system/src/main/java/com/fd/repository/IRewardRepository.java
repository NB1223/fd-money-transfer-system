package com.fd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fd.model.Reward;

@Repository
public interface IRewardRepository extends JpaRepository<Reward, Long>{
    //to get all the rewards for a certain account id
    //@Query(value = "SELECT * FROM reward WHERE reward.account_id = :accountId", nativeQuery = true)
    List<Reward> findByAccountId(Long accountId);

    //to check if a reward already exists for a transaction
    //@Query(value = "SELECT COUNT(*) > 0 FROM reward WHERE reward.transaction_id = :transactionId", nativeQuery = true)
    boolean existsByTransactionId(Long transactionId);
}
