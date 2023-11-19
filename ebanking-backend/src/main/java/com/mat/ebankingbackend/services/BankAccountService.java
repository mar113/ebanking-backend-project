package com.mat.ebankingbackend.services;

import com.mat.ebankingbackend.dtos.CurrentBankAccountDTO;
import com.mat.ebankingbackend.dtos.CustomerDTO;
import com.mat.ebankingbackend.dtos.SavingBankAccountDTO;
import com.mat.ebankingbackend.entities.BankAccount;
import com.mat.ebankingbackend.entities.CurrentAccount;
import com.mat.ebankingbackend.entities.Customer;
import com.mat.ebankingbackend.entities.SavingAccount;
import com.mat.ebankingbackend.exceptions.BalanceInsufficientException;
import com.mat.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.mat.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    public CustomerDTO saveCustomer(CustomerDTO customer);
    public CurrentBankAccountDTO createCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    public SavingBankAccountDTO createSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    public List<CustomerDTO> getCustomerList();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    public void Debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceInsufficientException;
    public void Credit(String accountId,double amount,String description) throws BankAccountNotFoundException;
    public void Transfer(String sourceAccountId, String destinationAccountId,double amount) throws BankAccountNotFoundException, BalanceInsufficientException;

    public Customer getCustomer(Long id) throws CustomerNotFoundException;
    public List<BankAccount> getBankAccountList();

    CustomerDTO getCustomerDTO(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId) throws CustomerNotFoundException;

}
