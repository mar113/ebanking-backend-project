package com.mat.ebankingbackend.dtos;

import com.mat.ebankingbackend.entities.AccountOperation;
import com.mat.ebankingbackend.entities.Customer;
import com.mat.ebankingbackend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
public class SavingBankAccountDTO {

    private String id;
    private Double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;




}
