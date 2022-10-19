package com.example.ebankb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
//des annotations Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    //on peut utiliser collectin car list implémente collection
    //lors de l ajout @oneToMany BankAccount doit etre obligatoirement une entité : @Entity
    @OneToMany(mappedBy = "customer")
    private List<BankAccount> bankAccounts;
}
