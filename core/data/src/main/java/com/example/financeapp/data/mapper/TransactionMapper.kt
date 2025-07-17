package com.example.financeapp.data.mapper

import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.data.local.database.entity.TransactionEntity
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.network.pojo.request.transaction.TransactionRequest
import com.example.financeapp.network.pojo.response.transaction.TransactionCreateResponse
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
        account = accountMapper.briefToDomain(response.account),
        category = categoryMapper.toDomain(response.category),
        amount = response.amount.toDoubleOrNull() ?: 0.0,
        date = response.transactionDate,
        comment = response.comment,
        isIncome = categoryMapper.toDomain(response.category).isIncome
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

    fun entityToDomain(
        response: TransactionEntity,
        account: AccountModel,
        category: CategoryModel
    ): TransactionModel = TransactionModel(
        id = response.remoteId ?: response.localId,
        account = account,
        category = category,
        amount = response.amount.toDoubleOrNull() ?: 0.0,
        date = response.transactionDate,
        comment = response.comment,
        isIncome = response.isIncome
    )

    fun toEntityOrNull(request: TransactionResponse): TransactionEntity? {
        val account = request.account ?: return null
        val category = request.category ?: return null
        return TransactionEntity(
            remoteId = request.id,
            accountId = account.id,
            categoryId = category.id,
            amount = request.amount.toString(),
            transactionDate = request.transactionDate,
            comment = request.comment,
            createdAt = request.createdAt,
            updatedAt = request.updatedAt,
            isIncome = category.isIncome,
            isSynced = false,
            lastSyncedAt = null
        )
    }

    fun toEntityFromCreateResponse(
        response: TransactionCreateResponse,
        isIncome: Boolean
    ): TransactionEntity = TransactionEntity(
        remoteId = response.id,
        accountId = response.accountId,
        categoryId = response.categoryId,
        amount = response.amount,
        transactionDate = response.transactionDate,
        comment = response.comment,
        createdAt = response.createdAt,
        updatedAt = response.updatedAt,
        isIncome = isIncome, // <-- передаём из репозитория!
        isSynced = true,
        lastSyncedAt = System.currentTimeMillis()
    )


    fun toEntity(request: TransactionResponse) = TransactionEntity(
        remoteId = request.id,
        accountId = request.account.id,
        categoryId = request.category.id,
        amount = request.amount.toString(),
        transactionDate = request.transactionDate,
        comment = request.comment,
        createdAt = "",
        updatedAt = "",
        isIncome = request.category.isIncome,
        isSynced = false,
        lastSyncedAt = null
    )

    fun toEntity(request: TransactionModel) = TransactionEntity(
        remoteId = request.id,
        accountId = request.account.id,
        categoryId = request.category.id,
        amount = request.amount.toString(),
        transactionDate = request.date,
        comment = request.comment,
        createdAt = "",
        updatedAt = "",
        isIncome = request.isIncome,
        isSynced = false,
        lastSyncedAt = null
    )
}
