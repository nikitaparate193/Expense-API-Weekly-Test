package com.example.Expense.API.service;

import com.example.Expense.API.model.AuthenticationToken;
import com.example.Expense.API.model.User;
import com.example.Expense.API.repository.IAuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    IAuthenticationRepo authenticationRepo;

    public boolean authenticate(String email, String authTokenValue) {
        AuthenticationToken authToken = authenticationRepo.findFirstByTokenValue(authTokenValue);

        if (authToken == null) {
            return false;
        }

        String tokenConnectedEmail = authToken.getUser().getUserEmail();

        return tokenConnectedEmail.equals(email);

    }

    public void saveAuthToken(AuthenticationToken authToken) {
        authenticationRepo.save(authToken);
    }

}
