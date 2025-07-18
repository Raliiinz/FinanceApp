package com.example.financeapp.data.repository.local

import com.example.financeapp.data.local.database.dao.AccountDao
import com.example.financeapp.data.mapper.AccountMapper
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.repository.local.AccountLocalRepository
import com.example.financeapp.util.result.FailureReason
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.financeapp.util.result.Result

class AccountLocalRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val accountMapper: AccountMapper
) : AccountLocalRepository {


    override suspend fun saveAccounts(accounts: List<AccountModel>) = withContext(Dispatchers.IO) {
        val entities = accounts.map { accountMapper.toEntity(it) }
        accountDao.insertAccounts(entities)
    }

    override suspend fun getCachedAccounts(): Result<List<AccountModel>> = withContext(Dispatchers.IO) {
        val localAccounts = accountDao.getAllAccounts()
        Result.Success(localAccounts.map { accountMapper.entityToDomain(it) })
    }

    override suspend fun updateAccount(
        account: AccountModel,
        isSynced: Boolean
    ) = withContext(Dispatchers.IO) {
        val entity = accountMapper.toEntity(account).copy(isSynced = isSynced)
        accountDao.updateAccount(entity)
    }

    // Оффлайн-логика обновления аккаунта
    override suspend fun updateAccountOffline(
        id: Int,
        name: String,
        balance: String,
        currency: String
    ): Result<AccountModel> = withContext(Dispatchers.IO) {
        val local = accountDao.getById(id)
        local?.let {
            val unsynced = it.copy(
                name = name,
                balance = balance,
                currency = currency,
                isSynced = false
            )
            accountDao.updateAccount(unsynced)
            Result.Success(accountMapper.entityToDomain(unsynced))
        } ?: Result.HttpError(FailureReason.NotFound("Account not found in local database"))
    }
}