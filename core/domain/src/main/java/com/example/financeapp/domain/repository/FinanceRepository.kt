package com.example.financeapp.domain.repository

import com.example.financeapp.domain.model.Category
import com.example.financeapp.domain.model.Check
import com.example.financeapp.domain.model.Expense
import com.example.financeapp.domain.model.Income

interface FinanceRepository {
    suspend fun getExpenses(): List<Expense>?
    suspend fun getIncomes(): List<Income>?
    suspend fun getChecks(): List<Check>?
    suspend fun getCategories(): List<Category>?
}