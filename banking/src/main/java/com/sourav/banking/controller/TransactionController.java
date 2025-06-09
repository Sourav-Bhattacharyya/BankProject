package com.sourav.banking.controller;

import com.sourav.banking.entity.Transaction;
import com.sourav.banking.repositoires.UserRepository;
import com.sourav.banking.security.JWTUtil;
import com.sourav.banking.service.TransactionService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/tranfer")
    public Transaction transfer(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount
    ){
        String token=authHeader.replace("Bearer","");
        String username = jwtUtil.extractUserName(token);
        Long userId = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Username not found")).getId();
        return transactionService.transfer(from, to, amount, userId);
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUser(@PathVariable Long userId){
        return transactionService.getTransactionsForUSer(userId);
    }

    @GetMapping("/user/{accountNumber}")
    public List<Transaction> getTransactionsByAccount(@PathVariable String accountNumber){
        return transactionService.getTransactionForAccount(accountNumber);
    }
}
