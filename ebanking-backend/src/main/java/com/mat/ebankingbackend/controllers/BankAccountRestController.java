package com.mat.ebankingbackend.controllers;

import com.mat.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;
}
