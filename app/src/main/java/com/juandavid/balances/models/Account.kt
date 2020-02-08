package com.juandavid.balances.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var name: String?,
    @Ignore var total: Int? = null
) {
    constructor() : this(null, null)
}

