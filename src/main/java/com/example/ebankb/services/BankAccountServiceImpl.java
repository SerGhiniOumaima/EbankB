package com.example.ebankb.services;

import com.example.ebankb.dtos.CustomerDTO;
import com.example.ebankb.entities.*;
import com.example.ebankb.enums.OperationType;
import com.example.ebankb.exceptions.BalanceNotSufficentException;
import com.example.ebankb.exceptions.BankAccountNotFoundException;
import com.example.ebankb.exceptions.CustomerNotFoundException;
import com.example.ebankb.mappers.BankAccountMapperImpl;
import com.example.ebankb.repositories.AccountOperationRepository;
import com.example.ebankb.repositories.BankAccountRepository;
import com.example.ebankb.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;

    //injection du mapper
    private BankAccountMapperImpl dtoMapper;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        log.info("Saving new customer");
        Customer customer =dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)

            throw new CustomerNotFoundException("Client non trouvé");

        CurrentAccount currentAccount=new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedCurrentAccount=bankAccountRepository.save(currentAccount);
        return savedCurrentAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestedRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)

            throw new CustomerNotFoundException("Client non trouvé");

        SavingAccount savingAccount=new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setInterestRate(interestedRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedSavingAccount=bankAccountRepository.save(savingAccount);
        return savedSavingAccount;
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        /*//stocker les entités de la bd dans customers
        List<Customer> customers=customerRepository.findAll();
        //créer un tableau qui va contenir des CustomerDTO
        List<CustomerDTO> customerDTOS=new ArrayList<>();
        //boucler sur customers , transformer chaque customer en customerDTO puis le stocker dans le tableau
        for(Customer customer:customers)
        {
            CustomerDTO customerDTO=dtoMapper.fromCustomer(customer);
            customerDTOS.add(customerDTO);
        }
        */
         //Autre manière de faire la meme chose
        List<Customer> customers=customerRepository.findAll();
        List<CustomerDTO> customerDTOS= customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {

        BankAccount bankAccount= bankAccountRepository.findById(accountId).
                orElseThrow(()->new BankAccountNotFoundException("Compte non trouvé"));

        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException,BalanceNotSufficentException{

        BankAccount bankAccount=getBankAccount(accountId);
        if(bankAccount.getBalance()<amount)
        {
            throw new BalanceNotSufficentException("Solde non suffisant");
        }else
        {

            AccountOperation accountOperation=new AccountOperation();
            accountOperation.setOperationDate(new Date());
            accountOperation.setAmount(amount);
            accountOperation.setType(OperationType.DEBIT);
            accountOperation.setDescription(description);
            accountOperation.setBankAccount(bankAccount );
            accountOperationRepository.save(accountOperation);
            bankAccount.setBalance(bankAccount.getBalance()-amount);
            bankAccountRepository.save(bankAccount);
        }



    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {

        BankAccount bankAccount=getBankAccount(accountId);


            AccountOperation accountOperation=new AccountOperation();
            accountOperation.setOperationDate(new Date());
            accountOperation.setAmount(amount);
            accountOperation.setType(OperationType.CREDIT);
            accountOperation.setDescription(description);
            accountOperation.setBankAccount(bankAccount );
            accountOperationRepository.save(accountOperation);
            bankAccount.setBalance(bankAccount.getBalance()+amount);
            bankAccountRepository.save(bankAccount);


    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException {
        debit(accountIdSource,amount,"transfer");
        credit(accountIdDestination,amount,"transfer");

    }
    @Override
    public List<BankAccount> bankAccountList(){
        List<BankAccount> bankAccounts=bankAccountRepository.findAll();
        return bankAccounts;
    }
    //getCustomer: afficher un customer depuis son id
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer Not found"));
        return dtoMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {

        Customer customer =dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId)
    {
        customerRepository.deleteById(customerId);
    }

}
