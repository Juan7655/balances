package com.juandavid.balances.database

import androidx.room.*
import com.juandavid.balances.models.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM account_transaction WHERE idAccount=:idAccount")
    fun getAccountTransactions(idAccount: Int): List<Transaction>

    @Query("SELECT * FROM account_transaction")
    fun getAll(): List<Transaction>

    @Insert
    fun insertTransaction(transaction:Transaction)

    @Insert
    fun insert(transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Query("DELETE FROM account_transaction")
    fun deleteAll()
}