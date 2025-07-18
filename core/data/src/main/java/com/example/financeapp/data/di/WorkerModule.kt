//package com.example.financeapp.data.di
//
//import android.content.Context
//import androidx.work.WorkManager
//import com.example.financeapp.base.di.qualifiers.ApplicationContext
//import com.example.financeapp.base.di.scopes.AppScope
//import com.example.financeapp.data.local.database.AppDatabase
//import com.example.financeapp.data.mapper.AccountMapper
//import com.example.financeapp.data.mapper.TransactionMapper
//import com.example.financeapp.data.repository.remote.TransactionRemoteRepositoryImpl
//import com.example.financeapp.data.worker.NetworkStateMonitor
//import com.example.financeapp.data.worker.SyncManager
//import com.example.financeapp.data.worker.SyncService
//import com.example.financeapp.data.worker.SyncWorker
//import com.example.financeapp.data.worker.WorkerAssistedFactory
//import com.example.financeapp.domain.repository.AccountRepository
//import com.example.financeapp.domain.repository.TransactionRepository
//import com.example.financeapp.network.util.NetworkChecker
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//
//@Module
//abstract class WorkerModule {
//
//    @Binds
//    abstract fun bindSyncWorkerFactory(factory: SyncWorker.Factory): WorkerAssistedFactory<SyncWorker>
//
//    companion object {
//        @Provides
//        @AppScope
//        fun provideSyncManager(
//            workManager: WorkManager,
//            networkChecker: NetworkChecker
//        ): SyncManager {
//            return SyncManager(workManager, networkChecker)
//        }
//
//        @Provides
//        @AppScope
//        fun provideSyncService(
//            transactionRemoteRepositoryImpl: TransactionRemoteRepositoryImpl,
//            accountRepository: AccountRepository,
//            accountMapper: AccountMapper,
//            transactionMapper: TransactionMapper,
//            database: AppDatabase,
//            networkChecker: NetworkChecker
//        ): SyncService {
//            return SyncService(
//                transactionRemoteRepositoryImpl,
//                accountRepository,
//                accountMapper,
//                transactionMapper,
//                database,
//                networkChecker
//            )
//        }
//
//        @Provides
//        @AppScope
//        fun provideNetworkStateMonitor(
//            @ApplicationContext context: Context,
//            syncManager: SyncManager
//        ): NetworkStateMonitor {
//            return NetworkStateMonitor(context, syncManager)
//        }
//    }
//}