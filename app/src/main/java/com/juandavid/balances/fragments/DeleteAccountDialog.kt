package com.juandavid.balances.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DeleteAccountDialog(private val position: Int) : DialogFragment() {
    private lateinit var listener: DeleteAccountListener

    interface DeleteAccountListener {
        fun onDeleteDialogPositiveClick(dialog: DialogFragment, position: Int)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DeleteAccountListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement DeleteAccountListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage("Delete Account")
                .setMessage("Are you sure you want to delete this Account?")
                .setPositiveButton("Yes") { _, _ ->
                    listener.onDeleteDialogPositiveClick(this, position)
                }.setNegativeButton("No") { _, _ -> listener.onDialogNegativeClick(this) }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}