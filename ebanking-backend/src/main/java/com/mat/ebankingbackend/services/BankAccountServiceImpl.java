package com.mat.ebankingbackend.services;

import com.mat.ebankingbackend.dtos.CustomerDTO;
import com.mat.ebankingbackend.dtos.SavingBankAccountDTO;
import com.mat.ebankingbackend.entities.*;
import com.mat.ebankingbackend.enums.AccountStatus;
import com.mat.ebankingbackend.enums.TypeOperation;
import com.mat.ebankingbackend.exceptions.BalanceInsufficientException;
import com.mat.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.mat.ebankingbackend.exceptions.CustomerNotFoundException;
import com.mat.ebankingbackend.mappers.BankAccountMapperImpl;
import com.mat.ebankingbackend.repositories.AccountOperationRepository;
import com.mat.ebankingbackend.repositories.BankAccountRepository;
import com.mat.ebankingbackend.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BankAccountServiceImpl implements  BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl mapper;
    public BankAccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository,BankAccountMapperImpl mapper) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.accountOperationRepository = accountOperationRepository;
        this.mapper = mapper;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Creation d'un utilisateur");
        Customer customer = mapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return mapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentAccount createCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()){
            throw new CustomerNotFoundException("Customer with id "+customerId+" not found");
        }

        CurrentAccount bankAccount = new CurrentAccount();
        bankAccount.setCreatedAt(new Date());
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCustomer(customer.get());
        bankAccount.setBalance(initialBalance);
        bankAccount.setOverDraft(overDraft);
        bankAccount.setStatus(AccountStatus.CREATED);

        return bankAccountRepository.save(bankAccount);
    }
    @Override
    public SavingBankAccountDTO createSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = getCustomer(customerId);

        SavingAccount bankAccount = new SavingAccount();

        bankAccount.setCreatedAt(new Date());
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCustomer(customer);
        bankAccount.setBalance(initialBalance);
        bankAccount.setInterstRate(interestRate);
        bankAccount.setStatus(AccountStatus.CREATED);

         SavingAccount savedBankAccount = bankAccountRepository.save(bankAccount);
         return  mapper.fromSavingBankAccount(savedBankAccount);
    }
    @Override
    public List<CustomerDTO> getCustomerList() {
        List<Customer> customers= customerRepository.findAll();
        List<CustomerDTO> customerDTOList = customers.stream().map(customer -> mapper.fromCustomer(customer)).collect(Collectors.toList());
      /*  List<CustomerDTO> customerDTOList = new ArrayList<>();
        for(Customer customer:customers){
            customerDTOList.add(mapper.fromCustomer(customer));
        }*/
        return customerDTOList;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountId);
        if(bankAccount.isEmpty()){
            throw new BankAccountNotFoundException("BankAccount with id"+accountId+"Not found");
        }
        return bankAccount.get();
    }
public void Debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceInsufficientException {
    BankAccount bankAccount = getBankAccount(accountId);
    if(bankAccount.getBalance()<amount)
        throw new BalanceInsufficientException("Balance insufficient");
    AccountOperation accountOperation = new AccountOperation();
    log.info("Starting debit operation");
    accountOperation.setTypeOperation(TypeOperation.DEBIT);
    accountOperation.setBankAccount(bankAccount);
    accountOperation.setDescription(description);
    accountOperation.setDateOpertation(new Date());
    accountOperation.setAmount(amount);
    accountOperationRepository.save(accountOperation);
    bankAccount.setBalance(bankAccount.getBalance()-amount);
    bankAccountRepository.save(bankAccount);
}

    @Override
    public void Credit(String accountId, double amount,String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = getBankAccount(accountId);
        log.info("Starting credit operation");
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setTypeOperation(TypeOperation.CREDIT);
        accountOperation.setDescription(description);
        accountOperation.setDateOpertation(new Date());
        accountOperation.setAmount(amount);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void Transfer(String sourceAccountId, String destinationAccountId, double amount) throws BankAccountNotFoundException, BalanceInsufficientException {
    Debit(sourceAccountId,amount,"trnsfer from");
    Credit(destinationAccountId,amount,"transfer to");

    }

    @Override
    public Customer getCustomer(Long id) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            throw new CustomerNotFoundException("Customer with id "+id+" not found");
        }
        return customer.get();
    }

    @Override
    public List<BankAccount> getBankAccountList() {
        return bankAccountRepository.findAll();
    }
@Override
    public CustomerDTO getCustomerDTO(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
        return mapper.fromCustomer(customer);
    }
@Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Creation d'un utilisateur");
        Customer customer = mapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return mapper.fromCustomer(savedCustomer);
    }
@Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = getCustomer(customerId);
        customer.getBankAccounts().stream().forEach(bankAccount -> {
        bankAccount.getAccountOperationList().stream().forEach(accountOperation -> {
            accountOperationRepository.delete(accountOperation);
        });
        bankAccountRepository.delete(bankAccount);
    });
        customerRepository.deleteById(customerId);
    }
}
