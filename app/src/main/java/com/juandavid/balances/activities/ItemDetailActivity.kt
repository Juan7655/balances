package com.juandavid.balances.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juandavid.balances.R
import com.juandavid.balances.adapters.TransactionAdapter
import com.juandavid.balances.database.dbOperation
import com.juandavid.balances.fragments.DeleteTransactionDialog
import com.juandavid.balances.fragments.NewTransactionFragment
import com.juandavid.balances.models.Account
import com.juandavid.balances.models.Transaction
import kotlinx.android.synthetic.main.activity_item_detail.*

class ItemDetailActivity : AppCompatActivity(), NewTransactionFragment.NewTransactionListener,
    DeleteTransactionDialog.DeleteTransactionListener {

    private var account: Account? = null
    private var recyclerView: RecyclerView? = null
    private var transactions: MutableList<Transaction> = mutableListOf()
    private var accountBalance: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)

        accountBalance = findViewById(R.id.transaction_description_item)
        recyclerView = findViewById(R.id.transactions)
        val idAccount: Int = intent.getIntExtra("idAccount", -1)
        recyclerView?.layoutManager = LinearLayoutManager(this)

        account = dbOperation(this) { db -> db.accountDao().findById(idAccount) }
        title = account?.name

        calculateBalance()
        recyclerView?.adapter = TransactionAdapter(transactions, this)

        fab.setOnClickListener {
            NewTransactionFragment().showNow(supportFragmentManager, "Create new transaction")
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun calculateBalance(){
        transactions = dbOperation(this) { db ->
            db.transactionDao().getAccountTransactions(account?.id ?: -1)
        }.toMutableList()
        account?.total = transactions.sumBy { t -> t.value ?: 0 }
        accountBalance?.text = "$${account?.total ?: 0}"
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDeleteItemSelected(position: Int) =
        DeleteTransactionDialog(position).showNow(supportFragmentManager, "DeleteTransaction")


    override fun onDialogPositiveClick(dialog: DialogFragment, transaction: Transaction) {
        transaction.idAccount=account?.id?:-1
        dbOperation(this) { db -> db.transactionDao().insertTransaction(transaction) }
        account?.total = (account?.total?:0) + (transaction.value?:0)
        accountBalance?.text = "$${account?.total ?: 0}"

        transactions.add(transaction)
        recyclerView?.adapter?.notifyItemInserted(transactions.size)
    }

    override fun onDeleteDialogPositiveClick(dialog: DialogFragment, position: Int) {
        dbOperation(this) { db -> db.transactionDao().delete(transactions[position]) }
        transactions.removeAt(position)
        recyclerView?.adapter?.notifyItemRemoved(position)

        calculateBalance()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) = Unit
}
