package com.example.financeapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.financeapp.base.di.qualifiers.ApplicationContext
import com.example.financeapp.base.di.scopes.AppScope
import com.example.financeapp.data.local.database.AppDatabase
import com.example.financeapp.data.local.database.dao.AccountDao
import com.example.financeapp.data.local.database.dao.CategoryDao
import com.example.financeapp.data.local.database.dao.TransactionDao
import dagger.Module
import dagger.Provides

@Module
object RoomModule {
    private val databaseName = "finance_db"

    @Provides
    @AppScope
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build()

    @Provides fun provideAccountDao(db: AppDatabase): AccountDao = db.accountDao()
    @Provides fun provideCategoryDao(db: AppDatabase): CategoryDao = db.categoryDao()
    @Provides fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao()
}