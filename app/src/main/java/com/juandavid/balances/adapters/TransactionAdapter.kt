package com.juandavid.balances.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juandavid.balances.R
import com.juandavid.balances.fragments.DeleteTransactionDialog
import com.juandavid.balances.models.Transaction


class TransactionAdapter(private val values: MutableList<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    override fun getItemCount(): Int = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvText?.text = values[position].description
        holder.tvContent?.text = "$${values[position].value}"
        holder.tvDate?.text = values[position].date.toString()
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvText: TextView? = null
        var tvContent: TextView? = null
        var tvDate: TextView? = null

        init {
            tvText = itemView.findViewById(R.id.transaction_description_item)
            tvContent = itemView.findViewById(R.id.transaction_value_item)
            tvDate= itemView.findViewById(R.id.transaction_date_item)
            itemView.setOnLongClickListener {
                Log.d("TAG", "OnLongClick pressed")
//                val dtd=DeleteTransactionDialog(position)
//                dtd.showNow(dtd.fragmentManager!!, "lol")
                true
            }

        }
    }

}