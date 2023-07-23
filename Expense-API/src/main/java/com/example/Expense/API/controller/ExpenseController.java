package com.example.Expense.API.controller;

import com.example.Expense.API.model.Expense;
import com.example.Expense.API.model.User;
import com.example.Expense.API.service.AuthenticationService;
import com.example.Expense.API.service.ExpenseService;
import com.example.Expense.API.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@Validated
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @Autowired
    UserService userService;

    @GetMapping("expense")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("expenses/{date}")
    public List<Expense> getExpensesByDate(@PathVariable LocalDate date) {
        return expenseService.getExpensesByDate(date);
    }

    @GetMapping("totalExpenditure")
    public double getTotalExpenditure(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                      @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return expenseService.calculateTotalExpenditure(startDate, endDate);
    }

    @PutMapping("updatePrice/{id}")
    public Expense updateExpensePrice(@PathVariable Long id, @RequestParam float newPrice) {
        return expenseService.updateExpensePrice(id, newPrice);
    }



}
