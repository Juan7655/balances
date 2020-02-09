package com.juandavid.balances.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juandavid.balances.R
import com.juandavid.balances.models.Account


class AccountAdapter(private val values: MutableList<Account>, private val listener: NewAccountListener) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {
    override fun getItemCount(): Int = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
        return ViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvAccountName.text = values[position].name
        holder.tvresult.text = "$${values[position].total}"
    }

    class ViewHolder(itemView: View, listener: NewAccountListener) :
        RecyclerView.ViewHolder(itemView) {
        var tvAccountName: TextView = itemView.findViewById(R.id.text_account_name)
        var tvresult: TextView = itemView.findViewById(R.id.text_account_value)

        init {
            itemView.setOnClickListener { listener.onMessageClick(adapterPosition) }
            itemView.setOnLongClickListener { listener.onDeleteItemSelected(adapterPosition) }
        }
    }

    interface NewAccountListener {
        fun onMessageClick(position: Int)
        fun onDeleteItemSelected(position: Int): Boolean
    }

}