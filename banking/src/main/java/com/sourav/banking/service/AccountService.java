package com.sourav.banking.service;

import com.sourav.banking.entity.Account;
import com.sourav.banking.repositoires.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    //create Account
    public Account createAccount(Long userId){
        Account account = new Account();
        account.setUserId(userId);
        account.setAccountNumber(UUID.randomUUID().toString().substring(0,12));
        account.setBalance(0.0);
        account.setCreatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    // Deposit Money
    public Account deposit(String accountNumber, double amount){
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()->new RuntimeException("Account Not found"));
        account.setBalance(account.getBalance()+amount);
        return accountRepository.save(account);
    }

    //Withdraw money
    public Account withdraw(String accountNumber, double amount){
        Account account=accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new RuntimeException("Account not found"));
        if(account.getBalance()<amount)throw new RuntimeException("Insufficient Account Balance");
        account.setBalance(account.getBalance()-amount);
        return accountRepository.save(account);
    }

    public Account getAccount(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new RuntimeException("Account not found"));
    }

    public List<Account> getAccountsByUser(Long userId){
        return accountRepository.findByUserId(userId);
    }

    public void deleteAccount(String accountNumber){
        Account account= accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new RuntimeException("Account not found"));
        accountRepository.delete(account);
    }
}
