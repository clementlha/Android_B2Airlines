package com.example.flightapp2022.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flightapp2022.FlightInfoCell
import com.example.flightapp2022.models.FlightModel
import com.example.flightapp2022.R
import java.text.SimpleDateFormat
import java.util.*

class FlightMapAdapter(
    private val flightList: List<FlightModel>
) :
RecyclerView.Adapter<FlightMapAdapter.FlightMapCellViewHolder>(), View.OnClickListener {

    class FlightMapCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val aeroDepart = itemView.findViewById<TextView>(R.id.AeroDept)!!
        val aeroArrive = itemView.findViewById<TextView>(R.id.AeroArr)!!
        val numeroVol = itemView.findViewById<TextView>(R.id.NumVol)!!
        val timeDepart = itemView.findViewById<TextView>(R.id.TimeStart)!!
        val timeArrive = itemView.findViewById<TextView>(R.id.TimeEnd)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightMapCellViewHolder {
        val cell = FlightInfoCell(parent.context)
        cell.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return FlightMapCellViewHolder(cell)
    }

    override fun onBindViewHolder(holder: FlightMapCellViewHolder, position: Int) {

        val flight = flightList[position]
        with(holder){
            aeroDepart.text = flight.estDepartureAirport
            aeroArrive.text = flight.estArrivalAirport
            numeroVol.text = flight.callsign
            timeDepart.text = getDateTime(flight.firstSeen)
            timeArrive.text = getDateTime(flight.lastSeen)

        }

    }
    private fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("H:m - MM/dd/yyyy")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
    override fun getItemCount(): Int {
        return flightList.size
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}
