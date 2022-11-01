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
    //modifier un client
    @PutMapping("/customers/{customerId}")
    //on a pas utilisé name de @PathVariable car l'id de l url et le param ont le même nom
    public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO)
    {
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }
    //supprimer un client
    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId)
    {
        bankAccountService.deleteCustomer( customerId);
    }

}
