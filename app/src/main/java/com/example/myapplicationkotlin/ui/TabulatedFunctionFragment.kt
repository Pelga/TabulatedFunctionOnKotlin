package com.example.myapplicationkotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationkotlin.R
import com.example.myapplicationkotlin.domain.ArrayTabulatedFunction
import com.example.myapplicationkotlin.domain.Constants.Companion.DIALOG
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.Serializable

class TabulatedFunctionFragment : Fragment, Serializable {
    var tabulatedFunctionFragmentViewModel: TabulatedFunctionFragmentViewModel? = null
    var tabulatedFunctionDialogFragment: TabulatedFunctionDialogFragment? = null
    var tabAdapter: TabAdapter? = null
    var array: ArrayTabulatedFunction? = null

    constructor(array: ArrayTabulatedFunction?) {
        this.array = array
    }

    constructor() {}

    open override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_my, container, false)
        tabulatedFunctionFragmentViewModel = ViewModelProvider(requireActivity()).get(
            TabulatedFunctionFragmentViewModel::class.java
        )
        if (array != null) {
            tabulatedFunctionFragmentViewModel!!.setArrayTabulatedFunction(array!!)
        }
        observeFragmentViewModel(view)
        return view
    }

    open fun openDialog() {
        tabulatedFunctionDialogFragment = TabulatedFunctionDialogFragment()
        tabulatedFunctionDialogFragment!!.show(getChildFragmentManager(), DIALOG)
    }

    open override fun onPause() {
        closeDialog()
        super.onPause()
    }

    open fun closeDialog() {
        if (tabulatedFunctionDialogFragment != null) {
            tabulatedFunctionDialogFragment?.dismiss()
        }
    }

    open fun observeFragmentViewModel(view: View) {
        var recyclerView: RecyclerView = view.findViewById(R.id.recycler_view2)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        tabAdapter = TabAdapter()
        recyclerView.adapter = tabAdapter

        tabulatedFunctionFragmentViewModel?.arrayTabulatedFunctionMutableLiveData?.observe(
            viewLifecycleOwner
        ) { array -> tabAdapter?.saveList(array) }
        tabulatedFunctionFragmentViewModel?.openDialogFragmentLiveData?.observe(
            viewLifecycleOwner
        ) { b -> openDialog() }

        var addButton: FloatingActionButton = view.findViewById(R.id.add_button)
        var buttonDatabase: Button = view.findViewById(R.id.button_database)

        tabulatedFunctionFragmentViewModel?.makeToastLiveData?.observe(
            viewLifecycleOwner
        ) { string -> Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show() }

        addButton.setOnClickListener(View.OnClickListener { view1: View ->
            tabulatedFunctionFragmentViewModel?.addButtonPressed(
                false
            )
        })
        buttonDatabase.setOnClickListener(View.OnClickListener { view1: View ->
            tabulatedFunctionFragmentViewModel?.buttonDatabasePressed(
                tabAdapter!!.list
            )
        })
    }
}