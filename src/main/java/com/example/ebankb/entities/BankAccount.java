package com.example.ebankb.entities;

import com.example.ebankb.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
//annotation pour dire qu'il y a des classes qui vont hérité de cette classe
//création de 2 tables au nv de la base de données mais obligatoirement mettre la classe BankAccount abstraite et elle
//sera vide dans la BD
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//création de 3 tables
//@Inheritance(strategy = InheritanceType.JOINED)
//ji
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//annotation pour ajouter dans la table compte la colonne type avec un max 4 caractères
@DiscriminatorColumn(name = "TYPE" , length = 4, discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor @AllArgsConstructor
public class BankAccount {
    @Id
    private String id ;
    private Double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer ;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperations;



}
