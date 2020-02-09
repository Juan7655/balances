package com.juandavid.balances.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juandavid.balances.R
import com.juandavid.balances.fragments.DeleteTransactionDialog.DeleteTransactionListener
import com.juandavid.balances.models.Transaction


class TransactionAdapter(
    private val values: MutableList<Transaction>,
    private val listener: DeleteTransactionListener
) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    override fun getItemCount(): Int = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvText.text = values[position].description
        holder.tvContent.text = "$${values[position].value}"
        holder.tvDate.text = values[position].date.toString()
    }

    class ViewHolder(itemView: View, listener: DeleteTransactionListener) :
        RecyclerView.ViewHolder(itemView) {
        var tvText: TextView = itemView.findViewById(R.id.transaction_description_item)
        var tvContent: TextView = itemView.findViewById(R.id.transaction_value_item)
        var tvDate: TextView = itemView.findViewById(R.id.transaction_date_item)

        init {
            itemView.setOnLongClickListener {
                listener.onDeleteItemSelected(position = adapterPosition)
                true
            }

        }
    }

}