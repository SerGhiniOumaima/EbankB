package com.example.ebankb.mappers;

import com.example.ebankb.dtos.CustomerDTO;
import com.example.ebankb.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    //le mapper contient des méthodes
    //je donne au mapper un objet et me retourne un autre objet
    //fromCustomer est une méthode qui prend un customer en param et retourne un customerDTO
    public CustomerDTO fromCustomer (Customer customer)
    {
        //BeanUtils copie les attributes d'1 objet dans un autre
        CustomerDTO customerDTO=new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        //customerDTO.setId(customer.getId());
        //customerDTO.setName(customer.getName());
        //customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }

    //fromCustomerDTO est une méthode qui prend un customerDTO en param et retourne un customer

    public Customer fromCustomerDTO (CustomerDTO customerDTO)
    {
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
}
