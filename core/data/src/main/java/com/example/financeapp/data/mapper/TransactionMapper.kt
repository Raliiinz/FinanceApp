package com.example.financeapp.data.mapper

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.network.pojo.request.transaction.TransactionRequest
import com.example.financeapp.network.pojo.response.transaction.TransactionResponse
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Маппер для преобразования между сетевыми и доменными моделями транзакций.
 */
@AppScope
class TransactionMapper @Inject constructor(
    private val accountMapper: AccountMapper,
    private val categoryMapper: CategoryMapper
) {
    private val isoDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .withZone(ZoneId.of("UTC"))

    fun toDomain(response: TransactionResponse): TransactionModel = TransactionModel(
        id = response.id,
        account = accountMapper.briefToDomain(response.account), // Assuming response.account is present for GETs
        category = categoryMapper.toDomain(response.category), // Assuming response.category is present for GETs
        amount = response.amount.toDoubleOrNull() ?: 0.0,
        date = response.transactionDate, // Make sure TransactionModel has 'date' or 'time' consistently
        comment = response.comment,
        isIncome = categoryMapper.toDomain(response.category).isIncome // <-- isIncome из CategoryModel
    )

    fun toRequest(transaction: TransactionModel): TransactionRequest {
        val formattedDate = try {
            val instant = Instant.parse(transaction.date)
            isoDateTimeFormatter.format(instant)
        } catch (e: Exception) {
            isoDateTimeFormatter.format(Instant.now())
        }
        return TransactionRequest(
            accountId = transaction.account.id,
            categoryId = transaction.category.id,
            amount = "%.2f".format(transaction.amount),
            transactionDate = formattedDate,
            comment = transaction.comment ?: ""
        )
    }
}
