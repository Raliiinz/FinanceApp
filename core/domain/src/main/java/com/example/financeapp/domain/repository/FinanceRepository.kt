package com.example.financeapp.domain.repository

import com.example.financeapp.domain.model.Category
import com.example.financeapp.domain.model.Check
import com.example.financeapp.domain.model.Expense
import com.example.financeapp.domain.model.Income

interface FinanceRepository {
    fun getExpenses(): List<Expense>?
    fun getIncomes(): List<Income>?
    fun getChecks(): List<Check>?
    fun getCategories(): List<Category>?
}