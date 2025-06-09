package com.sourav.banking.controller;

import com.sourav.banking.entity.Account;
import com.sourav.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create/{userId}")
    public Account create(@PathVariable Long userId){
        return  accountService.createAccount(userId);
    }

    @PostMapping("/withdraw")
    public Account withdraw(@RequestParam String accountNumber, @RequestParam double amount){
        return accountService.withdraw(accountNumber, amount);
    }

    @PostMapping("/deposit")
    public Account deposit(@RequestParam String accountNumber, @RequestParam double amount){
        return  accountService.deposit(accountNumber,amount);
    }

    @GetMapping("/{accountNumber}")
    public Account view(@PathVariable String accountNumber){
        return  accountService.getAccount(accountNumber);
    }

    @GetMapping("/user/{userId}")
    public List<Account> viewByUser(@PathVariable Long userId){
        return accountService.getAccountsByUser(userId);
    }

    @DeleteMapping("/{accountNumber}")
    public String delete(@PathVariable String accountNumber){
        accountService.deleteAccount(accountNumber);
        return "Account deleted successfully";
    }
}
