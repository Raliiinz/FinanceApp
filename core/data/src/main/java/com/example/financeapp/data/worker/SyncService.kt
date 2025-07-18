//package com.example.financeapp.data.worker
//
//import android.Manifest
//import androidx.annotation.RequiresPermission
//import com.example.financeapp.data.local.database.AppDatabase
//import com.example.financeapp.data.mapper.AccountMapper
//import com.example.financeapp.data.mapper.TransactionMapper
//import com.example.financeapp.domain.repository.AccountRepository
//import com.example.financeapp.domain.repository.CategoryRepository
//import com.example.financeapp.domain.repository.TransactionRepository
//import com.example.financeapp.domain.repository.local.TransactionLocalRepository
//import com.example.financeapp.domain.repository.remote.TransactionRemoteRepository
//import com.example.financeapp.network.util.NetworkChecker
//import com.example.financeapp.util.result.Result
//import javax.inject.Inject
//
//class SyncService @Inject constructor(
//    private val remoteRepo: TransactionRemoteRepository,
//    private val accountRepository: AccountRepository,
//    private val accountMapper: AccountMapper,
//    private val transactionMapper: TransactionMapper,
//    database: AppDatabase,
//    private val networkChecker: NetworkChecker
//) {
//    private val transactionDao = database.transactionDao()
//    private val accountDao = database.accountDao()
//
//    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
//    suspend fun syncAllData() {
//        if (!networkChecker.isNetworkAvailable()) return
//
//        // Синхронизация несинхронизированных транзакций
//        syncPendingTransactions()
//
//        // Синхронизация других данных (аккаунты, категории)
//        syncAccounts()
//    }
//
//    private suspend fun syncPendingTransactions() {
//        val unsynced = transactionDao.getUnsyncedTransactions()
//        for (entity in unsynced) {
//            when {
//                entity.isDeleted -> {
//                    val result = remoteRepo.deleteTransaction(entity.id)
//                    if (result is Result.Success) {
//                        transactionDao.deleteTransaction(entity.id)
//                        transactionDao.markTransactionAsSynced(entity.id)
//                    }
//                }
//
//                entity.isNew -> {
//                    // Маппим entity в доменную модель для отправки
//                    val transactionModel = transactionMapper.toDomain(entity)
//                    // Отправляем на сервер
//                    val result = remoteRepo.createTransaction(transactionModel)
//
//                    if (result is Result.Success) {
//                        // Сервер вернул нам транзакцию с настоящим ID.
//                        val newRemoteId = result.data.id
//                        // Обновляем нашу локальную запись:
//                        // - ставим новый remoteId
//                        // - убираем флаги isNew и isDeleted
//                        // - ставим флаг isSynced
//                        val syncedEntity = entity.copy(
//                            id = newRemoteId, // <--- САМОЕ ВАЖНОЕ
//                            isNew = false,
//                            isSynced = true
//                        )
//                        // Сначала удаляем старую запись с временным ID
//                        transactionDao.deleteTransaction(entity.id) // entity.id тут временный, локальный
//                        // Вставляем новую, уже синхронизированную
//                        transactionDao.insertTransaction(syncedEntity)
//                    } else {
//                        // Обработка ошибки, если не удалось создать на сервере
//                    }
////                    val result = remoteRepo.createTransaction(
////                        transactionMapper.toDomain(entity)
////                    )
////                    if (result is Result.Success) {
////                        transactionDao.updateTransaction(
////                            entity.copy(isSynced = true, isNew = false))
////                        transactionDao.markTransactionAsSynced(entity.id)
////                    }
//                }
//
//                else -> {
//                    val transactionModel = transactionMapper.toDomain(entity)
//                    val result = remoteRepo.updateTransaction(transactionModel)
//                    if (result is Result.Success) {
//                        // Просто помечаем локальную запись как синхронизированную
//                        transactionDao.markTransactionAsSynced(entity.id)
//                    } else {
//                        // Обработка ошибки
//                    }
////                    val result = remoteRepo.updateTransaction(
////                        transactionMapper.toDomain(entity)
////                    )
////                    if (result is Result.Success) {
////                        transactionDao.updateTransaction(entity.copy(isSynced = true))
////                        transactionDao.markTransactionAsSynced(entity.id)
////                    }
//                }
//            }
//        }
//    }
//
//    private suspend fun syncAccounts() {
//        val unsynced = accountDao.getUnsyncedAccounts()
//        for (entity in unsynced) {
//            val account = accountMapper.entityToDomain(entity)
//            accountRepository.updateAccount(
//                account.id,
//                account.name,
//                account.balance.toString(),
//                account.currency
//            )
//            accountDao.updateAccount(entity.copy(isSynced = true))
//            accountDao.markAccountAsSynced(entity.id)
//        }
//    }
//}