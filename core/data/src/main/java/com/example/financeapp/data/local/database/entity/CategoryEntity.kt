package com.example.financeapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "icon_leading")
    val iconLeading: String,
    @ColumnInfo(name = "text_leading")
    val textLeading: String,
    @ColumnInfo(name = "is_income")
    val isIncome: Boolean
)