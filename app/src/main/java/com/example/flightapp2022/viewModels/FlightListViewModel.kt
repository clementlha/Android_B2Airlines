package com.example.flightapp2022.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightapp2022.RequestManager
import com.example.flightapp2022.models.TrackingModel
import com.example.flightapp2022.models.FlightModel
import com.example.flightapp2022.models.PointPosModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.util.*

/**
 * Created by sergio on 25/10/2022
 * All rights reserved GoodBarber
 */
class FlightListViewModel : ViewModel() {

    private val flightListLiveData = MutableLiveData<List<FlightModel>>(ArrayList())
    private val clickedFlightLiveData = MutableLiveData<FlightModel>()

    fun getFlightListLiveData(): LiveData<List<FlightModel>> {
        return flightListLiveData
    }

    private fun setFlightListLiveData(flights: List<FlightModel>) {
        flightListLiveData.value = flights
    }

    fun getClickedFlightLiveData(): LiveData<FlightModel> {
        return clickedFlightLiveData
    }

    fun setClickedFlightLiveData(flight: FlightModel) {
        clickedFlightLiveData.value = flight
    }

    private fun setTrackingLiveData(tracking: TrackingModel) {
        MutableLiveData<TrackingModel>().value = tracking
    }

    fun getTrackingLiveData(): LiveData<TrackingModel> {
        return MutableLiveData<TrackingModel>()
    }

    fun doRequest(begin: Long, end: Long, isArrival: Boolean, icao: String) {

        viewModelScope.launch {

            val url = if (isArrival) "https://opensky-network.org/api/flights/arrival" else "https://opensky-network.org/api/flights/departure"
            val params = HashMap<String, String>()
            params.put("begin", begin.toString())
            params.put("end", end.toString())
            params.put("airport", icao)
            params.put("url",url)

            val result = withContext(Dispatchers.IO) {
                RequestManager.getSuspended(url, params)
            }
            if (result != null) {
                Log.i("REQUEST", result)
                val flightList = ArrayList<FlightModel>()
                val parser = JsonParser()
                val jsonElement = parser.parse(result)
                for (flightObject in jsonElement.asJsonArray) {
                    flightList.add(Gson().fromJson(flightObject.asJsonObject, FlightModel::class.java))
                }
                setFlightListLiveData(flightList)
                // Equivalent à
                //flightListLiveData.value =  flightList
            } else {
                Log.e("REQUEST", "ERROR NO RESULT")
            }
        }
    }
    fun getInformationTrack(){
        viewModelScope.launch {
            val selectedFlight = getClickedFlightLiveData().value!!
            val requestParameters = HashMap<String, String>()
            requestParameters["icao24"] = selectedFlight.icao24
            requestParameters["time"] = selectedFlight.firstSeen.toString()
            try {
                val result = withContext(Dispatchers.IO) {
                    RequestManager.getSuspended("https://opensky-network.org/api/tracks/all", requestParameters)
                }
                if (result != null) {
                    val JSONObjects = JSONObject(result)
                    val pos = ArrayList<PointPosModel>()
                    val tracking = TrackingModel(
                        JSONObjects.getString("icao24"),
                        JSONObjects.getLong("startTime"),
                        JSONObjects.getLong("endTime"),
                        JSONObjects.getString("callsign"),
                        pos
                    )
                    setTrackingLiveData(tracking)
                } else {
                    Log.e("REQUEST", "Aucun résultat")
                }
            } catch(e: IOException) {
                Log.e("REQUEST", "Erreur Accès Api")
            }
        }
    }
}