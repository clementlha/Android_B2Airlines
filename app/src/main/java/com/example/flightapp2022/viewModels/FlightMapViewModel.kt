package com.example.flightapp2022.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightapp2022.RequestManager
import com.example.flightapp2022.models.FlightModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlightMapViewModel : ViewModel() {

    private val FlightMapLiveData = MutableLiveData<List<FlightModel>>(ArrayList())

    private fun setFlightMapLiveData(data:List<FlightModel>?){
        FlightMapLiveData.value = data
    }

    fun getFlightMapLiveData(): MutableLiveData<List<FlightModel>> {
        return FlightMapLiveData
    }

    fun getInfoMapFlight(apiurl: String){

        viewModelScope.launch {

            var response = withContext(Dispatchers.IO){

                val response = HashMap<String, String>()
                RequestManager.getSuspended(apiurl,response)

            }

            val vol = object : TypeToken<List<FlightModel>>(){ }.type
            val flight = Gson().fromJson<List<FlightModel>>(response, vol)

            if (flight == null){

                setFlightMapLiveData(ArrayList())

            }else {

                setFlightMapLiveData(flight)

            }

        }
    }
}