package com.juandavid.balances.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.juandavid.balances.R
import kotlinx.android.synthetic.main.dialog_new_account.*

class NewAccountDialogFragment : DialogFragment() {
    private lateinit var listener: NewAccountListener

    interface NewAccountListener {
        fun onDialogPositiveClick(dialog: DialogFragment, accountName: String)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NewAccountListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement NewAccountListener")
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setMessage("Create new Account")
                .setView(inflater.inflate(R.layout.dialog_new_account, null))
                .setPositiveButton("Yes") { _, _ ->
                    val input: EditText? = this.dialog?.new_account_name
                    listener.onDialogPositiveClick(this, input?.text.toString())
                }.setNegativeButton("No") { _, _ ->
                    listener.onDialogNegativeClick(this)
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}