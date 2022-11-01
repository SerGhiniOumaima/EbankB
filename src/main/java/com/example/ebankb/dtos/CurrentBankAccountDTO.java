package com.example.ebankb.dtos;

import com.example.ebankb.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

//c'est un dto qui représente un compte courant 

@Data
public class CurrentBankAccountDTO {

    private String id ;
    private Double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO ;
    private double interestRate;




}
