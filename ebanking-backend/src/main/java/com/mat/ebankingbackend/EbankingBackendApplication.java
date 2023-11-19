package com.mat.ebankingbackend;

import com.mat.ebankingbackend.dtos.CustomerDTO;
import com.mat.ebankingbackend.entities.*;
import com.mat.ebankingbackend.enums.AccountStatus;
import com.mat.ebankingbackend.enums.TypeOperation;
import com.mat.ebankingbackend.exceptions.BalanceInsufficientException;
import com.mat.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.mat.ebankingbackend.exceptions.CustomerNotFoundException;
import com.mat.ebankingbackend.repositories.AccountOperationRepository;
import com.mat.ebankingbackend.repositories.BankAccountRepository;
import com.mat.ebankingbackend.repositories.CustomerRepository;
import com.mat.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}
@Bean
	CommandLineRunner start(BankAccountService bankAccountService){
		return args -> {
			Stream.of("Marwen","Ahmed","Ali").forEach(name->{
				CustomerDTO customer = new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
			System.out.println(bankAccountService.getCustomerList().size());
			bankAccountService.getCustomerList().forEach(customer -> {
				try {
					bankAccountService.createCurrentBankAccount(Math.random()*12000,0.0,customer.getId());
					bankAccountService.createSavingBankAccount(Math.random()*20000,5.5, customer.getId());

				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});
			List<BankAccount> bankAccountList = bankAccountService.getBankAccountList();
			System.out.println(bankAccountList.size());

			for(BankAccount bankAccount:bankAccountList) {


				try {
					bankAccountService.Credit(bankAccount.getId(), Math.random() * 10000, "Credit");
					bankAccountService.Debit(bankAccount.getId(), Math.random() * 1500, "Débit");
				} catch (BankAccountNotFoundException | BalanceInsufficientException e) {
					e.printStackTrace();
				}


			}

		};
	}
//@Bean
	CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository){
return args -> {
	Stream.of("marwen","Ahmed","Ali").forEach(name->{
		Customer customer = new Customer();
		customer.setName(name);
		customer.setEmail(name+"@gmail.com");
		customerRepository.save(customer);
	});
	customerRepository.findAll().forEach(customer -> {
		CurrentAccount currentAccount = new CurrentAccount();
		currentAccount.setId(UUID.randomUUID().toString());
		currentAccount.setBalance(Math.random()*12000);
		currentAccount.setStatus(AccountStatus.ACTIVATED);
		currentAccount.setCustomer(customer);
		currentAccount.setCreatedAt(new Date());
		currentAccount.setOverDraft(0);
		bankAccountRepository.save(currentAccount);

		SavingAccount savingAccount = new SavingAccount();
		savingAccount.setId(UUID.randomUUID().toString());
		savingAccount.setBalance(Math.random()*12000);
		savingAccount.setCustomer(customer);
		savingAccount.setStatus(AccountStatus.ACTIVATED);
		savingAccount.setInterstRate(4.2);
		savingAccount.setCreatedAt(new Date());
		bankAccountRepository.save(savingAccount);
	});
	bankAccountRepository.findAll().forEach(bankAccount -> {
for(int i=0;i<=4;i++){


		AccountOperation accountOperation = new AccountOperation();
			accountOperation.setBankAccount(bankAccount);
			accountOperation.setTypeOperation(Math.random()>0.5?TypeOperation.CREDIT:TypeOperation.DEBIT);
			accountOperation.setAmount(Math.random()*12000);
			accountOperation.setBankAccount(bankAccount);
			accountOperation.setDateOpertation(new Date());

			accountOperationRepository.save(accountOperation);
}
	});
};
	}
}
