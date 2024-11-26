package com.election.electionbackend.controllers.forum;

import com.election.electionbackend.models.forum.Account;
import com.election.electionbackend.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @GetMapping()
    public List<Account> getAccounts() {
        return accountService.findAll();
    }

}
