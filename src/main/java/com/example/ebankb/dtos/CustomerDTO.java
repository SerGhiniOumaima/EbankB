package com.example.ebankb.dtos;

import com.example.ebankb.entities.BankAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


//des annotations Lombok
@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
}