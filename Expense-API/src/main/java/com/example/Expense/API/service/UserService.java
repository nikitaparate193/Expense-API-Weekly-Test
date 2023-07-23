package com.example.Expense.API.service;

import com.example.Expense.API.model.AuthenticationToken;
import com.example.Expense.API.model.Expense;
import com.example.Expense.API.model.User;
import com.example.Expense.API.model.dto.SignInInput;
import com.example.Expense.API.model.dto.SignUpOutput;
import com.example.Expense.API.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ExpenseService expenseService;

    public SignUpOutput signUpUser(User user) {

        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null){
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null){
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        try{
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());
            user.setUserPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus,"User registered successfully!!!");
        }catch(Exception e){

            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus=false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);

        }
    }

    public String signInUser(SignInInput signInInput) {

        String signInStatusMessage = null;
        String signInEmail = signInInput.getEmail();
        if(signInEmail == null){
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;
        }

        User existingUser = userRepo.findFirstByUserEmail(signInEmail);
        if(existingUser == null){
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;
        }

        try{
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

                EmailHandler.sendEmail(signInEmail,"email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }catch(Exception e){
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }
    }

    public String createExpenses(Expense expense, String email) {
        User expenseOwner = userRepo.findFirstByUserEmail(email);
        expense.setExpenseOwner(expenseOwner);
        return expenseService.createExpenses(expense);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public String removeExpenses(Long id, String email) {
        User user = userRepo.findFirstByUserEmail(email);
        return expenseService.removeExpenses(id,user);
    }


}
