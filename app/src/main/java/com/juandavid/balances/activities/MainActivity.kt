package com.juandavid.balances.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juandavid.balances.R
import com.juandavid.balances.adapters.AccountAdapter
import com.juandavid.balances.database.dbOperation
import com.juandavid.balances.fragments.DeleteAccountDialog
import com.juandavid.balances.fragments.DeleteAccountDialog.DeleteAccountListener
import com.juandavid.balances.fragments.NewAccountDialogFragment
import com.juandavid.balances.models.Account
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AccountAdapter.NewAccountListener,
    NewAccountDialogFragment.NewAccountListener, DeleteAccountListener {

    private var accounts: MutableList<Account> = mutableListOf()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.accounts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            NewAccountDialogFragment().showNow(supportFragmentManager, "Create new account")
        }
    }

    override fun onPostResume() {
        super.onPostResume()

        accounts = dbOperation(this) { db -> db.accountDao().getAll()}.toMutableList()
        accounts.map(::calculateAccountTotal)
        recyclerView.adapter = AccountAdapter(accounts, this)
    }

    private fun calculateAccountTotal(account: Account): Account {
        account.total = dbOperation(this) { db ->  db.transactionDao()
            .getAccountTransactions(account.id ?: -1)
            .sumBy { t -> t.value?:0 }}
        return account
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onMessageClick(position: Int) {
        val intent = Intent(this, ItemDetailActivity::class.java)
        intent.putExtra("idAccount", accounts[position].id)
        startActivity(intent)
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, accountName: String) {
        val newAccount = Account(id = null, name = accountName, total = 0)
        val dbAccount = dbOperation(this) { db ->  db.accountDao().insertAll(newAccount)}[0]
        newAccount.id = dbAccount.toInt()

        accounts.add(newAccount)
        recyclerView.adapter?.notifyItemInserted(accounts.size - 1)
    }

    override fun onDeleteDialogPositiveClick(dialog: DialogFragment, position: Int) {
        dbOperation(this) { db -> db.accountDao().delete(accounts[position]) }
        accounts.removeAt(position)
        recyclerView.adapter?.notifyItemRemoved(position)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) = Unit
    override fun onDeleteItemSelected(position: Int): Boolean {
        DeleteAccountDialog(position).showNow(supportFragmentManager, "DeleteAccount")
        return true
    }
}
