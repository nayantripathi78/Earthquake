package com.example.earthquake

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val context: Context, private val data: List<Feature>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.earthquake_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = data[position]
        holder.magnitude.text = currentData.properties.mag.toString()
        holder.location.text = currentData.properties.place
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var magnitude: TextView
        var location: TextView
        var date: TextView
        var time: TextView

        init {
            magnitude = itemView.findViewById(R.id.magnitude)
            location = itemView.findViewById(R.id.location)
            date = itemView.findViewById(R.id.date)
            time = itemView.findViewById(R.id.time)
        }
    }
}