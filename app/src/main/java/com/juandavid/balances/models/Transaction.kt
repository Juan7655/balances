package com.juandavid.balances.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "account_transaction",
    foreignKeys = [(ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["idAccount"],
        onDelete = ForeignKey.CASCADE
    ))]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var value: Int?,
    var description: String?,
    var date: LocalDate = LocalDate.now(),
    var idAccount: Int?
) {
    constructor(value:Int, description: String) : this(null, value, description, idAccount = null)
}

