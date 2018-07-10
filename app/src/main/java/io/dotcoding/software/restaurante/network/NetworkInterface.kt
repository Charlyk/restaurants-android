package io.dotcoding.software.restaurante.network

import io.dotcoding.software.restaurante.models.MainResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkInterface {

    @GET("v2/venues/explore?")
    fun getRestaurants(@Query("ll") explore: String,
                       @Query("section") section: String = "food",
                       @Query("venuePhotos") venuePhotos: Int = 1,
                       @Query("oauth_token") token: String = "NKRP0KY5ZDZIBMCU3TZS4BMP4ZMIQZBQPLBTCPXSIGPWFJ1L",
                       @Query("v") v: String = "20160629"): Observable<MainResponse>

}