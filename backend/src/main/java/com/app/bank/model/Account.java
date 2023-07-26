package com.app.bank.model;

import java.util.HashMap;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Accounts")
public class Account {
    @Id
    private ObjectId id;

    private String userID;
    private String type;
    private int accountID;

    private float balance;
    private int transNum;

    private HashMap<Integer, Transaction> transHistory;

    public Account(String userID, String type) {
        this.userID = userID;
        this.type = type;
        accountID = 0;
        balance = 0;
        transHistory = new HashMap<>();
    }

    public String getUserID() {
        return userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public HashMap<Integer, Transaction> getTransHistory() {
        return transHistory;
    }

    public void setTransHistory(HashMap<Integer, Transaction> transHistory) {
        this.transHistory = transHistory;
    }

    public void addTrans(int transNum, Transaction trans) {
        transHistory.put(transNum, trans);
    }

    public void deposit(float amount) {
        balance += amount;
        transNum++;
        addTrans(transNum, new Transaction("deposit", amount));
    }

    public boolean withdraw(float amount) {
        if (amount <= balance) {
            balance -= amount;
            transNum++;
            addTrans(transNum, new Transaction("withdraw", amount));
            return true;
        }
        return false;
    }
}
