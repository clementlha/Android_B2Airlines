package com.example.flightapp2022.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.flightapp2022.FlightMapFragment
import com.example.flightapp2022.R
import com.example.flightapp2022.viewModels.FlightListViewModel
import com.example.flightapp2022.viewModels.FlightMapViewModel


class FlightMapActivity : AppCompatActivity() {
    private lateinit var viewModel: FlightMapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_flight_map)

        viewModel = ViewModelProvider(this).get(FlightMapViewModel::class.java)

        var icao= intent.getStringExtra("icao24")
        var lastSeen= intent.getStringExtra("lastSeen")
        var url= "https://opensky-network.org/api/tracks/all?icao24=" + icao + "&time=" + lastSeen

        viewModel.getInfoMapFlight(url)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val myFragment = FlightMapFragment()
        val b = Bundle()
        b.putString("url",intent.getStringExtra("url")  )
        b.putString("icao24",icao )

        myFragment.arguments=b
        fragmentTransaction.add(R.id.framelayout, myFragment).commit();

    }
}