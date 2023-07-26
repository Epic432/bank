package com.app.bank.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bank.model.Account;
import com.app.bank.service.AccountService;

@CrossOrigin(origins = "*")
@RequestMapping("api/v1/user/{userID}")
@RestController

public class AccountController {
    @Autowired
    private AccountService accountServices;

    @GetMapping(path = "/accounts")
    public ResponseEntity<List<Account>> getUserAccounts(@PathVariable("userID") String userID) {
        List<Account> userAccounts = accountServices.getUserAccounts(userID);
        return ResponseEntity.ok(userAccounts);
    }

    @GetMapping(path = "/{accountID}")
    public ResponseEntity<Account> getUserAccount(@PathVariable("accountID") int accountID) {
        Account account = accountServices.getAccount(accountID);
        return ResponseEntity.ok(account);
    }

    @PostMapping(path = "/{type}")
    public ResponseEntity<String> openAccount(@PathVariable("userID") String userID,
            @PathVariable("type") String type) {
        try {
            accountServices.newAccount(new Account(userID, type));
            return ResponseEntity.ok("Account opened successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred during account opening.");
        }
    }

    @PutMapping(path = "{accountID}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable("accountID") int accountID, @RequestBody float amount) {
        accountServices.depositAmount(accountID, amount);
        return new ResponseEntity<Account>(accountServices.getAccount(accountID), HttpStatus.OK);

    }

    @PutMapping(path = "{accountID}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable("accountID") int accountID,
            @RequestBody float amount) {
        try {
            accountServices.withdrawAmount(accountID, amount);
            return new ResponseEntity<Account>(accountServices.getAccount(accountID), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Account>(accountServices.getAccount(accountID), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "{accountID1}/{accountID2}")
    public ResponseEntity<String> transfer(@PathVariable("accountID1") int accountID1,
            @PathVariable("accountID2") int accountID2,
            @RequestBody float amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Invalid amount.");
        }
        try {
            accountServices.transfer(accountID1, accountID2, amount);
            return ResponseEntity.ok("Transfer successful.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during transfer.");
        }
    }

    @DeleteMapping(path = "/closeAll")
    public ResponseEntity<String> deleteAllUserAccounts(@PathVariable("userID") String userID) {
        try {
            accountServices.deleteUserAccounts(userID);
            return ResponseEntity.ok("Accounts closed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred.");
        }
    }

}
