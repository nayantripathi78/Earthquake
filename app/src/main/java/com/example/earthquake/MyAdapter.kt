package com.example.earthquake

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.floor

private const val LOCATION_SEPARATOR = " of "

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
        val currentEarthquake = data[position]
        holder.magnitude.text = formatMagnitude(currentEarthquake.properties.mag)

        val draw = holder.magnitude.background as GradientDrawable
        draw.setColor(context.getColor(getMagnitudeColor(currentEarthquake.properties.mag)))

        val originalLocation = currentEarthquake.properties.place
        val locationOffset: String
        val primaryLocation: String
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            val parts = originalLocation.split(LOCATION_SEPARATOR)
            locationOffset = parts[0] + LOCATION_SEPARATOR
            primaryLocation = parts[1]
        } else {
            locationOffset = context.getString(R.string.near_the)
            primaryLocation = originalLocation
        }

        holder.locationOffset.text = locationOffset
        holder.primaryLocation.text = primaryLocation

        val dateObj = Date(currentEarthquake.properties.time)
        holder.date.text = formatDate(dateObj)
        holder.time.text = formatTime(dateObj)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var magnitude: TextView
        var locationOffset: TextView
        var primaryLocation: TextView
        var date: TextView
        var time: TextView

        init {
            magnitude = itemView.findViewById(R.id.magnitude)
            locationOffset = itemView.findViewById(R.id.location_offset)
            primaryLocation = itemView.findViewById(R.id.primary_location)
            date = itemView.findViewById(R.id.date)
            time = itemView.findViewById(R.id.time)
        }
    }

    private fun getMagnitudeColor(mag: Double): Int {
        return when (floor(mag).toInt()) {
            1 -> R.color.magnitude1
            2 -> R.color.magnitude2
            3 -> R.color.magnitude3
            4 -> R.color.magnitude4
            5 -> R.color.magnitude5
            6 -> R.color.magnitude6
            7 -> R.color.magnitude7
            8 -> R.color.magnitude8
            9 -> R.color.magnitude9
            else -> R.color.magnitude10plus
        }
    }

    private fun formatMagnitude(mag: Double): String {
        val magFormat = DecimalFormat("0.0")
        return magFormat.format(mag)
    }

    private fun formatDate(dateObject: Date): String {
        val dateFormat = SimpleDateFormat("LLL dd, yyyy")
        return dateFormat.format(dateObject)
    }

    private fun formatTime(dateObject: Date): String {
        val timeFormat = SimpleDateFormat("hh:mm a")
        return timeFormat.format(dateObject)
    }

}