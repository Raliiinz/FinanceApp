package com.example.financeapp.data.repository.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.financeapp.data.local.database.dao.AccountDao
import com.example.financeapp.data.local.database.dao.CategoryDao
import com.example.financeapp.data.local.database.dao.TransactionDao
import com.example.financeapp.data.mapper.AccountMapper
import com.example.financeapp.data.mapper.CategoryMapper
import com.example.financeapp.data.mapper.TransactionMapper
import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.domain.repository.local.TransactionLocalRepository
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionLocalRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao,
    private val transactionMapper: TransactionMapper,
    private val accountMapper: AccountMapper,
    private val categoryMapper: CategoryMapper
) : TransactionLocalRepository {

    override suspend fun saveTransactions(transactions: List<TransactionModel>) =
        withContext(Dispatchers.IO) {
            transactionDao.insertTransactionList(transactions.map { transactionMapper.domainToEntity(it) })
        }

    override suspend fun deleteTransaction(id: Int) {
        withContext(Dispatchers.IO) {
            transactionDao.deleteTransaction(id)
        }
    }

    override suspend fun updateTransaction(transaction: TransactionModel) {
        withContext(Dispatchers.IO) {
            transactionDao.updateTransaction(transactionMapper.domainToEntity(transaction))
        }
    }

    override suspend fun addTransaction(transaction: TransactionModel,isSynced: Boolean) = withContext(Dispatchers.IO) {
        transactionDao.insertTransaction(transactionMapper.domainToEntity(transaction).copy(isSynced = isSynced))
    }

    override suspend fun getTransactionById(id: Int): TransactionModel = withContext(Dispatchers.IO) {
        transactionMapper.toDomain(transactionDao.getTransactionById(id))
    }

    override suspend fun getUnsyncedTransactions(): List<TransactionModel> {
        return transactionDao.getUnsyncedTransactions().map { transactionMapper.toDomain(it) }
    }

    override suspend fun markAsSynced(id: Int) {
        return transactionDao.markTransactionAsSynced(id)
    }

    override suspend fun getCachedTransactions(
        accountId: Int,
        from: String,
        to: String
    ): List<TransactionModel> =
        withContext(Dispatchers.IO) {
            transactionDao.getTransactionsForPeriod(accountId, from, to).map { transactionMapper.toDomain(it) }
        }

}