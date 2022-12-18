package com.example.flightapp2022

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.flightapp2022.models.FlightModel
import java.text.SimpleDateFormat
import java.util.*

class FlightInfoCell : LinearLayout {

    lateinit var depDateTextView: TextView
    lateinit var depAirportTextView: TextView
    lateinit var depHourTextView: TextView
    lateinit var arrDateTextView: TextView
    lateinit var arrAirportTextView: TextView
    lateinit var arrHourTextView: TextView
    lateinit var flightDurationTextView: TextView
    lateinit var flightNameTextView: TextView


    constructor(context: Context?) : super(context) {
        initLayout()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initLayout()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout()
    }

    private fun bindViews() {
        // make the find view by ids for your view
        depDateTextView = findViewById(R.id.depDateTextView)
        depAirportTextView= findViewById(R.id.depAirportTextView)
        depHourTextView= findViewById(R.id.depTimeTextView)
        arrDateTextView= findViewById(R.id.arrDateTextView)
        arrAirportTextView= findViewById(R.id.arrAirportTextView)
        arrHourTextView= findViewById(R.id.arrTimeTextView)
        flightDurationTextView= findViewById(R.id.flightDurationTextView)
        flightNameTextView= findViewById(R.id.flightNumberTextView)
    }

    fun bindData(flight: FlightModel) {
        Log.d("TAG", "message")
        //fill your views
        depDateTextView.text = getDateTime(flight.firstSeen)
        depAirportTextView.text = flight.estDepartureAirport
        //depHourTextView.text =
        flightNameTextView.text = flight.callsign
        flightDurationTextView.text = (flight.lastSeen - flight.firstSeen).toString()
        arrDateTextView.text = getDateTime(flight.lastSeen)
        arrAirportTextView.text = flight.estArrivalAirport
        //depHourTextView.text =
    }

    private fun initLayout() {
        LayoutInflater.from(context).inflate(R.layout.flight_cell, this, true)
        bindViews()
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

}