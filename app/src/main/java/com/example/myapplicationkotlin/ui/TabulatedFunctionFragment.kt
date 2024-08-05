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
import com.example.myapplicationkotlin.domain.Constants.DIALOG
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.Serializable

class TabulatedFunctionFragment(private val array: ArrayTabulatedFunction?) : Fragment(),
    Serializable {
    private var tabulatedFunctionFragmentViewModel: TabulatedFunctionFragmentViewModel? = null
    private var tabulatedFunctionDialogFragment: TabulatedFunctionDialogFragment? = null
    private var tabAdapter: TabAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_my, container, false)
        tabulatedFunctionFragmentViewModel =
            ViewModelProvider(requireActivity())[TabulatedFunctionFragmentViewModel::class.java]
        array?.let {
            tabulatedFunctionFragmentViewModel?.setArrayTabulatedFunction(it)
        }
        observeFragmentViewModel(view)
        return view
    }

    private fun openDialog() {
        tabulatedFunctionDialogFragment = TabulatedFunctionDialogFragment()
        tabulatedFunctionDialogFragment?.show(childFragmentManager, DIALOG)
    }

    override fun onPause() {
        closeDialog()
        super.onPause()
    }

    private fun closeDialog() {
        tabulatedFunctionDialogFragment?.dismiss()
    }

    private fun observeFragmentViewModel(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view2)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        tabAdapter = TabAdapter()
        recyclerView.adapter = tabAdapter

        tabulatedFunctionFragmentViewModel?.arrayTabulatedFunctionMutableLiveData?.observe(
            viewLifecycleOwner
        ) { array -> tabAdapter?.saveList(array) }
        tabulatedFunctionFragmentViewModel?.openDialogFragmentLiveData?.observe(
            viewLifecycleOwner
        ) { openDialog() }

        val addButton: FloatingActionButton = view.findViewById(R.id.add_button)
        val buttonDatabase: Button = view.findViewById(R.id.button_database)

        tabulatedFunctionFragmentViewModel?.makeToastLiveData?.observe(
            viewLifecycleOwner
        ) { string -> Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show() }

        addButton.setOnClickListener {
            tabulatedFunctionFragmentViewModel?.addButtonPressed(false)
        }

        buttonDatabase.setOnClickListener {
            tabAdapter?.list?.let { it1 ->
                tabulatedFunctionFragmentViewModel?.buttonDatabasePressed(
                    it1
                )
            }
        }
    }
}