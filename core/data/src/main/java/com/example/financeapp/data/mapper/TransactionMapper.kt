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
import java.time.LocalDateTime
import java.time.OffsetDateTime
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

    fun domainToEntity(transactionModel: TransactionModel): TransactionEntity {
        val parsedDateTime = LocalDateTime.parse(transactionModel.date)
        val date = parsedDateTime.toLocalDate().toString()
        val time = parsedDateTime.toLocalTime().withSecond(0).withNano(0).toString()

        return TransactionEntity(
            id = transactionModel.id,
            accountId = transactionModel.account.id.toString(),
            categoryId = transactionModel.category.id,
            categoryEmoji = transactionModel.category.iconLeading,
            currency = transactionModel.account.currency,
            categoryName = transactionModel.category.textLeading,
            isIncome = transactionModel.category.isIncome,
            amount = transactionModel.amount,
            time = time,
            date = date,
            comment = if (transactionModel.comment == "") null else transactionModel.comment
        )
    }

    fun entityToDomain(
        entity: TransactionEntity,
        account: AccountModel,
        category: CategoryModel
    ): TransactionModel {
        return TransactionModel(
            id = entity.id,
            account = account, // <-- Настоящая модель
            category = category, // <-- Настоящая модель
            isIncome = category.isIncome, // <-- Берем из настоящей категории
            amount = entity.amount,
            date = "${entity.date}T${entity.time}:00.000Z",
            comment = entity.comment
        )
    }

    fun toDomain(transactionEntity: TransactionEntity) = TransactionModel(
        id = transactionEntity.id,
        account = AccountModel(
            transactionEntity.accountId.toInt(),
            "",
            0.0,
            transactionEntity.currency
        ),
        category = CategoryModel(
            id = transactionEntity.categoryId,
            iconLeading = transactionEntity.categoryEmoji,
            textLeading = transactionEntity.categoryName,
            isIncome = transactionEntity.isIncome
        ),
        isIncome = transactionEntity.isIncome,
        amount = transactionEntity.amount,
        date = "${transactionEntity.date}T${transactionEntity.time}:00.000Z",
        comment = if (transactionEntity.comment == "") null else transactionEntity.comment
    )
}
