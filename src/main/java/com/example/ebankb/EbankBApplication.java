package com.example.ebankb;

import com.example.ebankb.entities.*;
import com.example.ebankb.enums.AccountStatus;
import com.example.ebankb.enums.OperationType;
import com.example.ebankb.exceptions.BalanceNotSufficentException;
import com.example.ebankb.exceptions.BankAccountNotFoundException;
import com.example.ebankb.exceptions.CustomerNotFoundException;
import com.example.ebankb.repositories.AccountOperationRepository;
import com.example.ebankb.repositories.BankAccountRepository;
import com.example.ebankb.repositories.CustomerRepository;
import com.example.ebankb.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBApplication.class, args);
    }
    @Bean
    CommandLineRunner start(BankAccountService  bankAccountService)
    {
        return args -> {
            Stream.of("Ali", "Amal", "Soukaina").forEach(name -> {
                Customer cus = new Customer();
                cus.setName(name);
                cus.setEmail(name + "@gmail.com");
                bankAccountService.saveCustomer(cus);
            });
            bankAccountService.listCustomers().forEach(customer ->
            {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,7000,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5, customer.getId());
                    List<BankAccount> bankAccounts=bankAccountService.bankAccountList();
                    for(BankAccount bankAccount:bankAccounts)
                    {
                        for(int i=0;i<10;i++)
                        {
                            bankAccountService.credit(bankAccount.getId(),12000*Math.random()+10000,"credit");
                            bankAccountService.debit(bankAccount.getId(),12000*Math.random()+10000,"debit");
                        }
                    }

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                } catch (BankAccountNotFoundException  | BalanceNotSufficentException e) {
                    e.printStackTrace();
                }

            });

        };

    }
/*
première partie du test
    {
        return args -> {
            Stream.of("Hassan","Leila","Aicha").forEach(name->{
                Customer cus=new Customer();
                cus.setName(name);
                cus.setEmail(name + "@gmail.com");
                customerRepository.save(cus);
            });
            customerRepository.findAll().forEach(customer -> {

                //pour chaque customer créer un current account et un saving account
                CurrentAccount currentAccount=new CurrentAccount();
                //UUID : nbmre en exa decimal : un string
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCreatedAt(new Date());
                //AccountStatus est une enum
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                //appeler l'interface mère bankAccountRepository sachantque les 2 types de comptes n'ont pas de RepositoryJPA
                bankAccountRepository.save(currentAccount);


                SavingAccount savingAccount =new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*9000);
                savingAccount.setCreatedAt(new Date());
                //AccountStatus est une enum
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(5.5);
                //appeler l'interface mère bankAccountRepository sachantque les 2 types de comptes n'ont pas de RepositoryJPA
                bankAccountRepository.save(savingAccount);

            });

            bankAccountRepository.findAll().forEach(bankAccount -> {
               for (int i=0 ;i<10;i++){
                   AccountOperation accountOperation= new AccountOperation();
                   accountOperation.setOperationDate(new Date());
                   accountOperation.setAmount(Math.random()*1200);
                   //Math.random génère un nombre entre 0 et 1 aléatoirement
                   accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                   accountOperation.setBankAccount(bankAccount);
                   accountOperationRepository.save(accountOperation);
               }

            });
        };
    }

 */
}
