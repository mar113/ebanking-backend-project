package com.mat.ebankingbackend.entities;

import com.mat.ebankingbackend.enums.TypeOperation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private Date dateOpertation;
    private String description;
    @Enumerated(EnumType.STRING)
    private TypeOperation typeOperation;
    @ManyToOne
    private BankAccount bankAccount;
}
