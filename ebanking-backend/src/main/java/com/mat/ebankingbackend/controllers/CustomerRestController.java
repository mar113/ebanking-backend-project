package com.mat.ebankingbackend.controllers;

import com.mat.ebankingbackend.dtos.CustomerDTO;
import com.mat.ebankingbackend.entities.Customer;
import com.mat.ebankingbackend.exceptions.CustomerNotFoundException;
import com.mat.ebankingbackend.services.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;
    public CustomerRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }


    @GetMapping("/all")
    public List<CustomerDTO> getAllCustomer(){
        return bankAccountService.getCustomerList();
    }
    @GetMapping("/customer/{id}")
    public Customer getCustomer(@PathVariable Long id) throws CustomerNotFoundException {
        return  bankAccountService.getCustomer(id);
    }

    @PostMapping()
    public CustomerDTO addCustomer(@RequestBody CustomerDTO request){
        return  bankAccountService.saveCustomer(request);

    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(id);
       return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(id);
    }
}
