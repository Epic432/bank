package com.app.bank.repo;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.bank.model.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, ObjectId> {

    Optional<Account> getAccountByAccountID(int accountID);

    List<Account> getAccountsByUserID(String userID);

    void deleteAllAccountsByUserID(String userID);

}
