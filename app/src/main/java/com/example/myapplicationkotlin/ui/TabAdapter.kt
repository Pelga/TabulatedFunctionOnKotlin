package com.example.myapplicationkotlin.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationkotlin.R
import com.example.myapplicationkotlin.domain.ArrayTabulatedFunction
import com.example.myapplicationkotlin.domain.FunctionPoint

class TabAdapter : RecyclerView.Adapter<TabAdapter.TabViewHolder>(), java.io.Serializable {
    val list = ArrayList<FunctionPoint>()

    override fun onCreateViewHolder(

        viewGroup: ViewGroup,
        i: Int
    ): TabViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.tabulated_function, viewGroup, false)
        return TabViewHolder(view)
    }

    override fun onBindViewHolder(
        tabViewHolder: TabViewHolder,
        i: Int
    ) {
        tabViewHolder.getButton().text = list[i].toString()
        tabViewHolder.getButton().setOnClickListener(
            (
                    { delete(i) })
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun saveList(
        list: ArrayTabulatedFunction
    ) {
        for (i in 0 until list.getPointsCount()) {
            this.list.remove(list.getPoint(i))
            this.list.add(list.getPoint(i)!!)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun delete(position: Int) {
        list.removeAt(position)
        notifyDataSetChanged()
    }

    class TabViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tabulatedButton: Button

        fun getButton(): Button {
            return tabulatedButton
        }

        init {
            tabulatedButton = itemView.findViewById(R.id.tabulatedButton)
        }
    }
}