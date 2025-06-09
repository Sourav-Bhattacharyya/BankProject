package com.sourav.banking.service;

import com.sourav.banking.entity.Account;
import com.sourav.banking.entity.Transaction;
import com.sourav.banking.repositoires.AccountRepository;
import com.sourav.banking.repositoires.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction transfer(String fromAcc, String toAcc, double amount, Long userId){
        Account from = accountRepository.findByAccountNumber(fromAcc)
                .orElseThrow(()-> new RuntimeException("Account not found"));
        Account to= accountRepository.findByAccountNumber(toAcc)
                .orElseThrow(()->new RuntimeException("Account not found"));

        if(from.getBalance()<amount) throw new RuntimeException("Insufficient Account balance");

        from.setBalance(from.getBalance()-amount);
        to.setBalance(to.getBalance()+amount);

        accountRepository.save(from);
        accountRepository.save(to);

        Transaction txn = new Transaction(null, fromAcc, toAcc, amount, "Transfer successful", LocalDateTime.now(), userId);
        return transactionRepository.save(txn);
    }

    public Transaction recordTransaction(String type, String accountNumber, double amount, Long userId){
        Transaction transaction = new Transaction();
        transaction.setFromAccount(type.equals("WITHDRAW")?accountNumber:null);
        transaction.setToAccount(type.equals("DEPOSIT")?accountNumber:null);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setTimeStamp(LocalDateTime.now());
        transaction.setUserId(userId);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsForUSer(Long userId){
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> getTransactionForAccount(String accountNumber){
        return transactionRepository.findByFromAccountOrToAccount(accountNumber,accountNumber);
    }
}
