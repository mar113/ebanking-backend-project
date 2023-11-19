package com.mat.ebankingbackend.mappers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.mat.ebankingbackend.dtos.CurrentBankAccountDTO;
import com.mat.ebankingbackend.dtos.CustomerDTO;
import com.mat.ebankingbackend.dtos.SavingBankAccountDTO;
import com.mat.ebankingbackend.entities.CurrentAccount;
import com.mat.ebankingbackend.entities.Customer;
import com.mat.ebankingbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {

    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }

    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){
        SavingBankAccountDTO savingAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingAccountDTO);
        return savingAccountDTO;
    }

    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingAccountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO,savingAccount);
        return savingAccount;
    }
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO currentAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentAccountDTO);
        return currentAccountDTO;
    }

    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO,currentAccount);
        return currentAccount;
    }
}
