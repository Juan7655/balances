package com.juandavid.balances.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.juandavid.balances.models.Account
import com.juandavid.balances.models.Transaction

@Database(entities = [Account::class, Transaction::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DB : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}

fun <T> dbOperation(context: Context, operation: (DB) -> T): T {
    val db = Room.databaseBuilder(
        context,
        DB::class.java, "database-balances"
    ).allowMainThreadQueries().build()
    val result = operation(db)
    db.close()
    return result
}
