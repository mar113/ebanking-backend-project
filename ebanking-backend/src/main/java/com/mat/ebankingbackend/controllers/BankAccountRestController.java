package com.mat.ebankingbackend.controllers;

import com.mat.ebankingbackend.entities.BankAccount;
import com.mat.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.mat.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/all")
    public List<BankAccount> getBankAccounts(){
        return bankAccountService.getBankAccountList();
    }

    @GetMapping("/{id}")
    public BankAccount getBankAccount(@PathVariable String id) throws BankAccountNotFoundException {
       return bankAccountService.getBankAccount(id);
    }
}


