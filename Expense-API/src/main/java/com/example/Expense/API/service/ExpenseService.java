package com.example.Expense.API.service;

import com.example.Expense.API.model.Expense;
import com.example.Expense.API.model.User;
import com.example.Expense.API.repository.IExpenseRepo;
import com.example.Expense.API.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class ExpenseService {

    @Autowired
    IExpenseRepo expenseRepo;

    @Autowired
    IUserRepo userRepo;


    public String createExpenses(Expense expense) {
        expense.setDate(expense.getDate());
        expenseRepo.save(expense);
        return "Expenses Created!!!";
    }

    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    public String removeExpenses(Long id, User user) {
        expenseRepo.deleteAll();
        return "user expense deleted";
    }


    public List<Expense> getExpensesByDate(LocalDate date) {
        return expenseRepo.findExpensesByDate(date);
    }




    public double calculateTotalExpenditure(LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = expenseRepo.findByDateBetween(startDate, endDate);
        return expenses.stream().mapToDouble(Expense::getPrice).sum();
    }

    public Expense updateExpensePrice(Long id, float newPrice) {
        Optional<Expense> optionalExpense = expenseRepo.findById(id);
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setPrice(newPrice);
            return expenseRepo.save(expense);
        } else {
            throw new NoSuchElementException("Expense not found with ID: " + id);
        }
    }
}
