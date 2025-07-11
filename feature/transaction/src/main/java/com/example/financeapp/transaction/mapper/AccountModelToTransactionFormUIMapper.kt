package com.example.financeapp.transaction.mapper

import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.transaction.model.TransactionFormAccountUI
import javax.inject.Inject

/**
 * Маппер для преобразования [AccountModel] в его UI-представление [TransactionFormAccountUI].
 */
class AccountModelToTransactionFormUIMapper @Inject constructor() {
    fun map(domain: AccountModel) = TransactionFormAccountUI(domain.id, domain.name, domain.currency)
    fun mapList(domains: List<AccountModel>) = domains.map { map(it) }
 }