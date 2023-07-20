package com.app.bank.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "Users")
public class Person {

    @Id
    ObjectId id;

    private String userID;
    private String password;

    @DocumentReference
    private List<Account> accounts;

    public Person(String userID, String password) {
        this.userID = userID;
        this.password = password;
        accounts = new ArrayList<>();

    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public List<Account> getAccountList() {
        return accounts;
    }

    public int getNumOfAccounts() {
        return accounts.size();
    }

}
