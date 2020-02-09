package com.juandavid.balances.database

import androidx.room.*
import com.juandavid.balances.models.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAll(): List<Account>

    @Query(value="SELECT * FROM account WHERE id=:idAccount")
    fun findById(idAccount: Int): Account

    @Insert
    fun insertAll(vararg accounts: Account): List<Long>

    @Delete
    fun delete(user: Account)

}