package com.example.financeapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.financeapp.data.local.database.dao.AccountDao
import com.example.financeapp.data.local.database.dao.CategoryDao
import com.example.financeapp.data.local.database.dao.TransactionDao
import com.example.financeapp.data.local.database.entity.AccountEntity
import com.example.financeapp.data.local.database.entity.CategoryEntity
import com.example.financeapp.data.local.database.entity.TransactionEntity

@Database(
    entities = [AccountEntity::class, CategoryEntity::class, TransactionEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
}