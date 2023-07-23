package com.example.Expense.API.repository;

import com.example.Expense.API.model.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface IExpenseRepo extends JpaRepository<Expense,Long> {


    List<Expense> findExpensesByDate(LocalDate date);



    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
