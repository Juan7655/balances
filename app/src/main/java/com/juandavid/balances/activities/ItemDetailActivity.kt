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
    private var myText2: RecyclerView? = null
    private var transactions: MutableList<Transaction> = mutableListOf()
    private var myText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)

        myText = findViewById(R.id.transaction_description_item)
        myText2 = findViewById(R.id.transactions)
        val idAccount: Int = intent.getIntExtra("idAccount", -1)
        myText2?.layoutManager = LinearLayoutManager(this)

        account = dbOperation(this) { db ->db.accountDao().findById(idAccount)}

        transactions = dbOperation(this) { db ->db.transactionDao().getAccountTransactions(account?.id ?: -1)}.toMutableList()
        myText2?.adapter = TransactionAdapter(transactions)
        account?.total = transactions.sumBy { t -> t.value ?: 0 }

        title = account?.name
        myText?.text = "$${account?.total ?: 0}"

        fab.setOnClickListener {
            NewTransactionFragment().showNow(supportFragmentManager, "Create new transaction")
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDialogPositiveClick(dialog: DialogFragment, transaction: Transaction) {
        transaction.idAccount = account?.id

        account?.total = (transaction.value ?: 0) + (account?.total ?: 0)
        myText?.text = "$${account?.total ?: 0}"
        dbOperation(this) { db ->db.transactionDao().insertTransaction(transaction)}

        transactions.add(transaction)
        myText2?.adapter?.notifyItemInserted(transactions.size)
    }

    override fun onDeleteDialogPositiveClick(dialog: DialogFragment, position:Int) {
        dbOperation(this) { db ->db.transactionDao().delete(transactions[position])}
        transactions.removeAt(position)
        myText2?.adapter?.notifyItemRemoved(position)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {

    }
}
