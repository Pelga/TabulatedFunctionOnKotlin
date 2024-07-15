package com.example.myapplicationkotlin.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationkotlin.R
import com.example.myapplicationkotlin.domain.Constants.Companion.CANCEL
import com.example.myapplicationkotlin.domain.Constants.Companion.DIALOG_TITLE
import com.example.myapplicationkotlin.domain.Constants.Companion.NULL
import com.example.myapplicationkotlin.domain.Constants.Companion.OK
import com.example.myapplicationkotlin.ui.TabulatedFunctionString.Companion.toStr
import java.io.Serializable

class TabulatedFunctionDialogFragment : DialogFragment(), Serializable {
    lateinit var xDialog2: EditText
    lateinit var yDialog2: EditText

    open override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myFragmentViewModel =
            ViewModelProvider(requireActivity()).get(TabulatedFunctionFragmentViewModel::class.java)
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog, null)
        builder.setView(view)
            .setTitle(DIALOG_TITLE)
            .setNegativeButton(CANCEL) { dialog, i -> }
            .setPositiveButton(OK) { dialog, i ->
                val strX: String = toStr(xDialog2)
                val strY: String = toStr(yDialog2)
                if (strX.trim { it <= ' ' } != NULL || strY == NULL) {
                    val x = strX.toDouble()
                    val y = strY.toDouble()
                    Log.d("1", "")
                    myFragmentViewModel.positiveButtonPressed(x, y)
                }
            }
        xDialog2 = view.findViewById(R.id.x_dialog2)
        yDialog2 = view.findViewById(R.id.y_dialog2)
        return builder.create()
    }
}