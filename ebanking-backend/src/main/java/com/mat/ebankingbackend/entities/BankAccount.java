package com.mat.ebankingbackend.entities;

import com.mat.ebankingbackend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(length = 4,name = "Type")
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
@Id
    private String id;
    private Double balance;
    private Date createdAt;

    private AccountStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount")
    private List<AccountOperation> accountOperationList;



}
