package com.juandavid.balances.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.juandavid.balances.R
import com.juandavid.balances.models.Transaction

class NewTransactionFragment : DialogFragment() {
    private lateinit var listener: NewTransactionListener

    interface NewTransactionListener {
        fun onDialogPositiveClick(dialog: DialogFragment, transaction: Transaction)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NewTransactionListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement NewTransactionListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setMessage("Register Transaction")
                .setView(inflater.inflate(R.layout.dialog_new_transaction, null))
                .setPositiveButton("Yes") { _, _ ->
                    val value: EditText? = this.dialog?.findViewById(R.id.transaction_value)
                    val description: EditText? =
                        this.dialog?.findViewById(R.id.transaction_description)
                    val splitCheckbox: CheckBox? =
                        this.dialog?.findViewById(R.id.split_transaction_checkbox)

                    val factor: Int = if (splitCheckbox?.isChecked == true) 2 else 1

                    val transaction = Transaction(
                        value = Integer.valueOf(value?.text.toString()) / factor,
                        description = description?.text.toString()
                    )

                    listener.onDialogPositiveClick(this, transaction)
                }.setNegativeButton("No") { _, _ -> listener.onDialogNegativeClick(this) }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}