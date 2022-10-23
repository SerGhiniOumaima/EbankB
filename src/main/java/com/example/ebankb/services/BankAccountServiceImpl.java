package com.example.ebankb.services;

import com.example.ebankb.entities.*;
import com.example.ebankb.enums.OperationType;
import com.example.ebankb.exceptions.BalanceNotSufficentException;
import com.example.ebankb.exceptions.BankAccountNotFoundException;
import com.example.ebankb.exceptions.CustomerNotFoundException;
import com.example.ebankb.repositories.AccountOperationRepository;
import com.example.ebankb.repositories.BankAccountRepository;
import com.example.ebankb.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    @Override
    public Customer saveCustomer(Customer customer) {

        log.info("Saving new customer");
        Customer savedCustomer =customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)

            throw new CustomerNotFoundException("Client non trouvé");

        CurrentAccount currentAccount=new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedCurrentAccount=bankAccountRepository.save(currentAccount);
        return savedCurrentAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestedRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)

            throw new CustomerNotFoundException("Client non trouvé");

        SavingAccount savingAccount=new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setInterestRate(interestedRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedSavingAccount=bankAccountRepository.save(savingAccount);
        return savedSavingAccount;
    }


    @Override
    public List<Customer> listCustomers() {
       return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {

        BankAccount bankAccount= bankAccountRepository.findById(accountId).
                orElseThrow(()->new BankAccountNotFoundException("Compte non trouvé"));

        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException,BalanceNotSufficentException{

        BankAccount bankAccount=getBankAccount(accountId);
        if(bankAccount.getBalance()<amount)
        {
            throw new BalanceNotSufficentException("Solde non suffisant");
        }else
        {

            AccountOperation accountOperation=new AccountOperation();
            accountOperation.setOperationDate(new Date());
            accountOperation.setAmount(amount);
            accountOperation.setType(OperationType.DEBIT);
            accountOperation.setDescription(description);
            accountOperation.setBankAccount(bankAccount );
            accountOperationRepository.save(accountOperation);
            bankAccount.setBalance(bankAccount.getBalance()-amount);
            bankAccountRepository.save(bankAccount);
        }



    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {

        BankAccount bankAccount=getBankAccount(accountId);


            AccountOperation accountOperation=new AccountOperation();
            accountOperation.setOperationDate(new Date());
            accountOperation.setAmount(amount);
            accountOperation.setType(OperationType.CREDIT);
            accountOperation.setDescription(description);
            accountOperation.setBankAccount(bankAccount );
            accountOperationRepository.save(accountOperation);
            bankAccount.setBalance(bankAccount.getBalance()+amount);
            bankAccountRepository.save(bankAccount);


    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException {
        debit(accountIdSource,amount,"transfer");
        credit(accountIdDestination,amount,"transfer");

    }
}
