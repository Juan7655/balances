package com.juandavid.balances.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DeleteTransactionDialog(private val position: Int) : DialogFragment() {
    private lateinit var listener: DeleteTransactionListener

    interface DeleteTransactionListener {
        fun onDeleteDialogPositiveClick(dialog: DialogFragment, position: Int)
        fun onDialogNegativeClick(dialog: DialogFragment)
        fun onDeleteItemSelected(position: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DeleteTransactionListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement DeleteTransactionListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage("Delete transaction")
                .setMessage("Are you sure you want to delete this transaction?")
                .setPositiveButton("Yes") { _, _ ->
                    listener.onDeleteDialogPositiveClick(this, position)
                }.setNegativeButton("No") { _, _ -> listener.onDialogNegativeClick(this) }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}