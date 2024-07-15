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

open class TabAdapter : RecyclerView.Adapter<TabAdapter.TabViewHolder>(), java.io.Serializable {
    open val list = ArrayList<FunctionPoint>()

    @NonNull
    override fun onCreateViewHolder(
        @NonNull
        viewGroup: ViewGroup,
        i: Int
    ): TabViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.tabulated_function, viewGroup, false)
        return TabViewHolder(view)
    }

    override fun onBindViewHolder(
        @NonNull
        tabViewHolder: TabViewHolder,
        i: Int
    ) {
        if (list.get(i) != null) {
            tabViewHolder.getButton().setText(list.get(i).toString())
        }
        tabViewHolder.getButton().setOnClickListener(
            (
                    { view -> delete(i) })
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun saveList(
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
    open fun delete(position: Int) {
        list.removeAt(position)
        notifyDataSetChanged()
    }

    open class TabViewHolder : RecyclerView.ViewHolder {

        val tabulatedButton: Button

        fun getButton(): Button {
            return tabulatedButton
        }

        constructor(itemView: View) : super(itemView) {
            tabulatedButton = itemView.findViewById(R.id.tabulatedButton)
        }
    }
}