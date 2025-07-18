package com.example.financeapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "account_id")
    val accountId: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "category_name")
    val categoryName: String,
    @ColumnInfo(name = "currency")
    val currency: String,
    @ColumnInfo(name = "category_emoji")
    val categoryEmoji: String,
    @ColumnInfo(name = "is_income")
    val isIncome: Boolean,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "comment")
    val comment: String?,
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false,
//    @ColumnInfo(name = "is_deleted")
//    val isDeleted: Boolean = false,
//    @ColumnInfo(name = "is_new")
//    val isNew: Boolean = false
)