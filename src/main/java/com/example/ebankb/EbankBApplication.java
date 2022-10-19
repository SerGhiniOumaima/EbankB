package com.example.ebankb;

import com.example.ebankb.entities.*;
import com.example.ebankb.enums.AccountStatus;
import com.example.ebankb.enums.OperationType;
import com.example.ebankb.repositories.AccountOperationRepository;
import com.example.ebankb.repositories.BankAccountRepository;
import com.example.ebankb.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBApplication.class, args);
    }
    @Bean
    CommandLineRunner start(
            CustomerRepository customerRepository,
            BankAccountRepository bankAccountRepository,
            AccountOperationRepository accountOperationRepository)

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
}