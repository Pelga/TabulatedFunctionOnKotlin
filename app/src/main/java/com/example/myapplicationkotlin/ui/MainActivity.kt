package com.example.myapplicationkotlin.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationkotlin.R
import com.example.myapplicationkotlin.domain.ArrayTabulatedFunction
import com.google.android.material.button.MaterialButton
import java.io.Serializable

open class MainActivity : AppCompatActivity(), Serializable {
  ///  val viewModel: MainActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel()
    }

    private fun closeKeyboard(view: View?) {
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }//?????????????????


    private fun openFragment(arrayTabulatedFunction: ArrayTabulatedFunction) {
        val fragmentManager = supportFragmentManager
        val fragment = TabulatedFunctionFragment(arrayTabulatedFunction)
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun observeViewModel() {
        val cardView = findViewById<CardView>(R.id.card)
        val leftDomainBorder = findViewById<EditText>(R.id.right_domain_border)
        val rightDomainBorder = findViewById<EditText>(R.id.left_domain_border)
        val pointsCount = findViewById<EditText>(R.id.points_count)
        val materialButtonCreate = findViewById<MaterialButton>(R.id.material_button)
        val materialButtonGenerate = findViewById<MaterialButton>(R.id.material_button_generate)
        val materialButtonDownload = findViewById<MaterialButton>(R.id.material_button_download)
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)//??

        viewModel.closeCardViewLiveData.observe(this) { boo ->
            cardView.visibility = if (boo) View.INVISIBLE else View.VISIBLE
        }
        viewModel.makeErrorToastLiveData.observe(this) { string ->
            Toast.makeText(this@MainActivity, string, Toast.LENGTH_LONG).show()
        }
        viewModel.arrayTabulatedFunctionLiveData
            .observe(this) { arrayTabulatedFunction: ArrayTabulatedFunction? ->
                openFragment(
                    arrayTabulatedFunction!!
                )
            }
        viewModel.closeKeyboardLiveData.observe(this, ::closeKeyboard)
        viewModel.visibilityLiveData.observe(this) { cardView.visibility = it }

        materialButtonCreate.setOnClickListener { view ->
            viewModel.materialButtonCreatePressed(
                rightDomainBorder,
                leftDomainBorder,
                pointsCount,
                view,
                View.INVISIBLE
            )
        }

        materialButtonGenerate.setOnClickListener { view ->
            viewModel.materialButtonGeneratePressed(view, View.INVISIBLE, true)
        }

        materialButtonDownload.setOnClickListener { view ->
            viewModel.materialButtonDownloadPressed(view, View.INVISIBLE, true)
        }
    }
}

