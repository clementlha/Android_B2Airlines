package com.example.flightapp2022.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.flightapp2022.FlightMapFragment
import com.example.flightapp2022.R
import com.example.flightapp2022.viewModels.FlightListViewModel

class FlightListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_liste)

        val isTablet = findViewById<FragmentContainerView>(R.id.fragment_map_container) != null


        val begin = intent.getLongExtra("BEGIN", 0)
        val end = intent.getLongExtra("END", 0)
        val isArrival = intent.getBooleanExtra("IS_ARRIVAL", false)
        val icao = intent.getStringExtra("ICAO")

        val viewModel = ViewModelProvider(this).get(FlightListViewModel::class.java)

        Log.i("MAIN ACTIVITY", "begin = $begin \n end = $end \n icao = $icao \n is arrival = $isArrival")



        viewModel.doRequest(begin, end, isArrival, icao!!)

        viewModel.getClickedFlightLiveData().observe(this, Observer {
            // Afficher le bon vol

          if (!isTablet) {
                //remplacer le fragment
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.fragment_list_container,
                    FlightMapFragment.newInstance("", "")
                )
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })

    }


}