package com.example.ebankb.mappers;

import com.example.ebankb.dtos.CurrentBankAccountDTO;
import com.example.ebankb.dtos.CustomerDTO;
import com.example.ebankb.dtos.SavingBankAccountDTO;
import com.example.ebankb.entities.BankAccount;
import com.example.ebankb.entities.CurrentAccount;
import com.example.ebankb.entities.Customer;
import com.example.ebankb.entities.SavingAccount;
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
    //mappers pour un saving account

    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO)
    {
           SavingAccount savingAccount=new SavingAccount();
           BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
           savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
           return savingAccount;
    }
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount)
    {
        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
        //je prends les customers de savingAccount je les transforme en customersDTO puis je les affecte à savingAccountDTO
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        return savingBankAccountDTO;
    }
    //mappers pour un current account
    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO)
    {
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount)
    {
        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        return currentBankAccountDTO;

    }
}
