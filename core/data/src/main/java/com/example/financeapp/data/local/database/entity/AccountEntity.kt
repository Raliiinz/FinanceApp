package com.example.financeapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "balance")
    val balance: String,
    @ColumnInfo(name = "currency")
    val currency: String,
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false
)
