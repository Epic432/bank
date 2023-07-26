package com.app.bank.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.app.bank.model.Account;
import com.app.bank.model.Person;
import com.app.bank.repo.AccountRepository;

@Service
public class AccountService {

    Random random = new Random();

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getUserAccounts(String userID) {
        return accountRepository.getAccountsByUserID(userID);
    }

    public Account getAccount(int accountID) {
        return accountRepository.getAccountByAccountID(accountID).get();
    }

    public Boolean isAccount(int accountID) {
        return accountRepository.getAccountByAccountID(accountID).isPresent();
    }

    public void newAccount(Account account) {
        int possAID = random.nextInt(4000, 6000);
        while (Boolean.TRUE.equals(isAccount(possAID))) {
            possAID = random.nextInt(4000, 6000);
        }
        account.setAccountID(possAID);
        accountRepository.insert(account);
        String userID = account.getUserID();
        mongoTemplate.update(Person.class)
                .matching(Criteria.where("userID").is(userID))
                .apply(new Update().push("accounts").value(account))
                .first();
    }

    public void deleteAccount(int accountID) {
        if (Boolean.TRUE.equals(isAccount(accountID))) {
            accountRepository.delete(getAccount(accountID));
        }
    }

    public void deleteUserAccounts(String userID) {
        accountRepository.deleteAllAccountsByUserID(userID);
    }

    public void depositAmount(int accountID, float amount) {
        if (Boolean.TRUE.equals(isAccount(accountID))) {
            Account account = getAccount(accountID);
            account.deposit(amount);
            accountRepository.save(account);
        }
    }

    public boolean withdrawAmount(int accountID, float amount) {
        if (Boolean.TRUE.equals(isAccount(accountID))) {
            Account account = getAccount(accountID);
            if (account.withdraw(amount)) {
                accountRepository.save(account);
                return true;
            }
        }
        return false;
    }

    public void transfer(int accountID1, int accountID2, float amount) {
        if (isAccount(accountID1) && isAccount(accountID2)) {
            Account account1 = getAccount(accountID1);
            account1.withdraw(amount);
            Account account2 = getAccount(accountID2);
            account2.deposit(amount);
            accountRepository.save(account1);
            accountRepository.save(account2);
        }

    }

    public void deleteAll() {
        accountRepository.deleteAll();
    }

}
