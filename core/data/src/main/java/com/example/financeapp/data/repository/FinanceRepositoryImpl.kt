package com.example.financeapp.data.repository

import com.example.financeapp.data.mapper.mapToCategory
import com.example.financeapp.data.mapper.mapToCheck
import com.example.financeapp.data.mapper.mapToExpenses
import com.example.financeapp.data.mapper.mapToIncome
import com.example.financeapp.data.model.AccountBrief
import com.example.financeapp.data.model.AccountResponse
import com.example.financeapp.data.model.CategoryResponse
import com.example.financeapp.data.model.StatItem
import com.example.financeapp.data.model.TransactionResponse
import com.example.financeapp.domain.model.Category
import com.example.financeapp.domain.model.Check
import com.example.financeapp.domain.model.Expense
import com.example.financeapp.domain.model.Income
import com.example.financeapp.domain.repository.FinanceRepository
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor() : FinanceRepository {
    override suspend fun getExpenses(): List<Expense>? {
        val expensesData = listOf(
            Triple("Аренда квартиры", "100 000 P", null),
            Triple("Одежда", "100 000 P", null),
            Triple("На собачку", "100 000 P", "Джек"),
            Triple("На собачку", "100 000 P", "Энни"),
            Triple("Ремонт квартиры", "100 000 P", null),
            Triple("Продукты", "100 000 P", null),
            Triple("Спортзал", "100 000 P", null),
            Triple("Медицина", "100 000 P", null)
        )

        val initialExpensesList = expensesData.mapIndexed { index, (name, amount, comment) ->
            TransactionResponse(
                id = index + 1,
                account = AccountBrief(
                    id = index + 1,
                    name = "Основной счёт",
                    balance = "-${amount.replace(" P", "").replace(" ", "")}",
                    currency = "RUB"
                ),
                category = CategoryResponse(
                    id = index + 1,
                    name = name,
                    emoji = when {
                        name == "Аренда квартиры" -> "🏠"
                        name == "Одежда" -> "👗"
                        name.contains("собачку") -> "🐶"
                        name == "Ремонт квартиры" -> "РК"
                        name == "Продукты" -> "🍭"
                        name == "Спортзал" -> "🏋️"
                        name == "Медицина" -> "💊"
                        else -> "💰"
                    },
                    isIncome = false
                ),
                amount = amount.replace(" P", "").replace(" ", ""),
                transactionDate = "2025-13-10T21:56:58.596Z",
                comment = comment,
                createdAt = "2025-13-10T21:56:58.596Z",
                updatedAt = "2025-13-10T21:56:58.596Z"
            )
        }
        return initialExpensesList.map { it.mapToExpenses() }
    }

    override suspend fun getIncomes(): List<Income>? {
        val initialIncomesList = mutableListOf<TransactionResponse>()
            .apply {
                add(
                    TransactionResponse(
                        id = 1,
                        account = AccountBrief(
                            id = 1,
                            name = "Основной счёт",
                            balance = "-670000.00",
                            currency = "RUB"
                        ),
                        category = CategoryResponse(
                            id = 1,
                            name = "Зарплата",
                            emoji = "💰",
                            isIncome = true
                        ),
                        amount = "500000.00",
                        transactionDate = "2025-06-10T21:56:58.596Z",
                        comment = "Зарплата за месяц",
                        createdAt = "2025-10-10T21:50:58.596Z",
                        updatedAt = "2025-10-10T21:50:58.596Z"
                    )
                )
                add(
                    TransactionResponse(
                        id = 2,
                        account = AccountBrief(
                            id = 2,
                            name = "Основной счёт",
                            balance = "-670000.00",
                            currency = "RUB"
                        ),
                        category = CategoryResponse(
                            id = 2,
                            name = "Подработка",
                            emoji = "💰",
                            isIncome = true
                        ),
                        amount = "100000.00",
                        transactionDate = "2025-06-10T21:56:58.596Z",
                        comment = "Подработка за месяц",
                        createdAt = "2025-12-10T19:50:58.596Z",
                        updatedAt = "2025-12-10T19:50:58.596Z"
                    )
                )

            }
        return initialIncomesList.map { it.mapToIncome() }
    }

    override suspend fun getCheck(): Check? {
        val initialAccount =
            AccountResponse(
                id = 1,
                name = "Основной счёт",
                balance = "-670000.00",
                currency = "₽",
                incomeStats = StatItem(
                    categoryId = 1,
                    categoryName = "Зарплата",
                    emoji = "💰",
                    amount = "5000.00"
                ),
                expenseStats = StatItem(
                    categoryId = 1,
                    categoryName = "Зарплата",
                    emoji = "💰",
                    amount = "5000.00"
                ),
                createdAt = "2025-06-12T23:10:08.275Z",
                updatedAt = "2025-06-12T23:10:08.275Z"
            )
        return initialAccount.mapToCheck()
    }

    override suspend fun getCategories(): List<Category> {
        val categoriesData = listOf(
            Triple(1, "Аренда квартиры", "🏠"),
            Triple(2, "Одежда", "👗"),
            Triple(3, "На собачку", "🐶"),
            Triple(4, "На собачку", "🐶"),
            Triple(5, "Ремонт квартиры", "РК"),
            Triple(6, "Продукты", "🍭"),
            Triple(7, "Спортзал", "🏋️"),
            Triple(8, "Медицина", "💊")
        )

        return categoriesData.map { (id, name, emoji) ->
            CategoryResponse(
                id = id,
                name = name,
                emoji = emoji,
                isIncome = false
            ).mapToCategory()
        }
    }
}