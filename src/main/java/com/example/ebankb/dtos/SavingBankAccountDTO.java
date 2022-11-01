package com.example.ebankb.dtos;

import com.example.ebankb.entities.AccountOperation;
import com.example.ebankb.entities.Customer;
import com.example.ebankb.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//c'est un dto qui représente un compte epargne

@Data
public class SavingBankAccountDTO {

    private String id ;
    private Double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO ;
    private double overDraft;




}
