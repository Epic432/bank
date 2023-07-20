package com.app.bank.model;

public class Transaction {
    private String type;
    private float amount;

    public Transaction(String type, float amount) {
        this.type = type;
        this.amount = amount;
    }

    public String toString() {
        return type + " " + amount;
    }
}
