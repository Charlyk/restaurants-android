package io.dotcoding.software.restaurante

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.dotcoding.software.restaurante.adapters.VenuesAdapter
import io.dotcoding.software.restaurante.models.Venue
import io.dotcoding.software.restaurante.utils.RApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {


    private var venues: ArrayList<Venue> = arrayListOf()
    private lateinit var adapter: VenuesAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var lat: Double = 0.0
    private var lng: Double = 0.0


    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getLastPosition()

        adapter = VenuesAdapter(this)
        recyclerView.adapter = adapter

        mainSwipeToRefresh.setOnRefreshListener { checkLocation() }


        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

    }


    @SuppressLint("MissingPermission")
    fun getLastPosition() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if(location != null) {
                        lat = location.latitude
                        lng = location.longitude
                        getData()
                    }
                }
    }


    fun getData() {
        val query = lat.toString() +","+lng.toString()
        RApplication.apiInterface().getRestaurants(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    venues.clear()
                    it.response.groups.forEach {
                        it.items.forEach {
                            venues.add(it.venue!!)
                        }
                    }
                    adapter.setItems(venues)
                    mainSwipeToRefresh.isRefreshing = false
                }, {
                    it.printStackTrace()
                    if(it is HttpException){
                        Log.d("test",it.message())
                    }
                    mainSwipeToRefresh.isRefreshing = false
                })
    }


    @SuppressLint("MissingPermission")
    fun checkLocation() {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            lat = location.latitude
            lng = location.longitude
            getData()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

}
