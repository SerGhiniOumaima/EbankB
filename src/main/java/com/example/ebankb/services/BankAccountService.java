package com.example.ebankb.services;

import com.example.ebankb.dtos.CustomerDTO;
import com.example.ebankb.entities.BankAccount;
import com.example.ebankb.entities.CurrentAccount;
import com.example.ebankb.entities.Customer;
import com.example.ebankb.entities.SavingAccount;
import com.example.ebankb.exceptions.BalanceNotSufficentException;
import com.example.ebankb.exceptions.BankAccountNotFoundException;
import com.example.ebankb.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService
{
    //traitement sur les comptes
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    //création d'un compte
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestedRate, Long customerId) throws CustomerNotFoundException;
    //liste des clients
    List<CustomerDTO> listCustomers();
    //consulter un compte
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId,double amount,String description) throws BankAccountNotFoundException;
    void transfert(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficentException;


    List<BankAccount> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
}
