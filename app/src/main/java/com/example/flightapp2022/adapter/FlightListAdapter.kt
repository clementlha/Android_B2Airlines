package com.example.flightapp2022.adapter

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flightapp2022.FlightInfoCell
import com.example.flightapp2022.models.FlightModel


class FlightListAdapter(
    var flightList: List<FlightModel>,
    val cellClickListener: OnCellClickListener
) :
    RecyclerView.Adapter<FlightListAdapter.FlightListCellViewHolder>() {

    interface OnCellClickListener {
        fun onCellClicked(flightModel: FlightModel)
    }

    class FlightListCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightListCellViewHolder {
        Log.i("CELL", "onCreateViewHolder")
        val cell = FlightInfoCell(parent.context)
        cell.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return FlightListCellViewHolder(cell)
    }

    override fun onBindViewHolder(holder: FlightListCellViewHolder, position: Int) {
        Log.i("CELL", "onBindViewHolder with position $position")

        val flight = flightList[position]
        val cell = holder.itemView as FlightInfoCell
        cell.setBackgroundColor(if (position % 2 == 0) Color.LTGRAY else Color.GRAY)
        cell.bindData(flight)

        cell.setOnClickListener {
            cellClickListener.onCellClicked(flight)
        }

    }

    override fun getItemCount(): Int {
        return flightList.size
    }
}