package com.example.ebankb.exceptions;

public class BalanceNotSufficentException extends Exception {
    public BalanceNotSufficentException(String msg) {
        super(msg);
    }
}
