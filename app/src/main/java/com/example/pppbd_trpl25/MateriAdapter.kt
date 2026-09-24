package com.example.pppbd_trpl25

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MateriAdapter(private val materiList: List<String>) :
    RecyclerView.Adapter<MateriAdapter.MateriViewHolder>() {

    class MateriViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val title: android.widget.TextView = itemView.findViewById(R.id.tv_materi_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_materi, parent, false)
        return MateriViewHolder(view)
    }

    override fun onBindViewHolder(holder: MateriViewHolder, position: Int) {
        holder.title.text = "${position + 1}. ${materiList[position]}"
    }

    override fun getItemCount(): Int = materiList.size
}