package com.example.ebankb.web;

import com.example.ebankb.dtos.CustomerDTO;
import com.example.ebankb.entities.Customer;
import com.example.ebankb.exceptions.BankAccountNotFoundException;
import com.example.ebankb.exceptions.CustomerNotFoundException;
import com.example.ebankb.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("customers/{id}")
    //@PathVariable(name="id") pour dire que customerId est l'id de getmapping
    public CustomerDTO getCustomer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException {

        return bankAccountService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    //@RequestBody pour indiquer à spring qu'on va récupérer les D de customerDTO à partir du body
    //de la requete sous format json
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO)
    {
          return bankAccountService.saveCustomer(customerDTO);
    }
}
