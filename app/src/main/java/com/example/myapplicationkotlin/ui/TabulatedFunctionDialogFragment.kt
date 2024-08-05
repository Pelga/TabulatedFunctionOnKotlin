package com.example.myapplicationkotlin.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationkotlin.R
import com.example.myapplicationkotlin.domain.Constants.CANCEL
import com.example.myapplicationkotlin.domain.Constants.DIALOG_TITLE
import com.example.myapplicationkotlin.domain.Constants.NULL
import com.example.myapplicationkotlin.domain.Constants.OK
import com.example.myapplicationkotlin.ui.TabulatedFunctionString.toStr
import java.io.Serializable

class TabulatedFunctionDialogFragment : DialogFragment(), Serializable {
    private lateinit var xDialog2: EditText
    private lateinit var yDialog2: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myFragmentViewModel =
            ViewModelProvider(requireActivity())[TabulatedFunctionFragmentViewModel::class.java]
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog, null)
        builder.setView(view)
            .setTitle(DIALOG_TITLE)
            .setNegativeButton(CANCEL) { _, _ -> }
            .setPositiveButton(OK) { _, _ ->
                val strX = toStr(xDialog2)
                val strY = toStr(yDialog2)
                if (strX.trim { it <= ' ' } != NULL || strY == NULL) {
                    val x = strX.toDouble()
                    val y = strY.toDouble()
                    myFragmentViewModel.positiveButtonPressed(x, y)
                }
            }
        xDialog2 = view.findViewById(R.id.x_dialog2)
        yDialog2 = view.findViewById(R.id.y_dialog2)
        return builder.create()
    }
}