package com.example.flightapp2022

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.flightapp2022.activity.FlightMapActivity
import com.example.flightapp2022.adapter.FlightMapAdapter
import com.example.flightapp2022.models.FlightModel
import com.example.flightapp2022.viewModels.FlightListViewModel
import com.example.flightapp2022.viewModels.FlightMapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class FlightMapFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var flights: MutableLiveData<List<FlightModel>>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FlightMapAdapter
    private lateinit var viewmodel: FlightMapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(FlightListViewModel::class.java)
        viewModel.getClickedFlightLiveData().observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.NumVol).text = it.callsign
            view.findViewById<TextView>(R.id.AeroDept).text = it.estDepartureAirport
            view.findViewById<TextView>(R.id.AeroArr).text = it.estArrivalAirport
            view.findViewById<TextView>(R.id.TimeStart).text = getDateTime(it.firstSeen)
            view.findViewById<TextView>(R.id.TimeEnd).text = getDateTime(it.lastSeen)
        })
    }
    private fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FlightMapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private val callback = OnMapReadyCallback { googleMap ->

        val viewModel = ViewModelProvider(requireActivity()).get(FlightListViewModel::class.java)
        viewModel.getInformationTrack()

        viewModel.getTrackingLiveData().observe(viewLifecycleOwner) {
            val markers = ArrayList<LatLng>()
            for (point in it.pos) {
                markers.add(LatLng(point.lat, point.lng))
            }

            googleMap.addPolyline(PolylineOptions().addAll(markers))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(markers[0]))
        }
    }
}