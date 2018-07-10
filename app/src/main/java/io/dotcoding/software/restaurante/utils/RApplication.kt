package io.dotcoding.software.restaurante.utils

import android.app.Application
import android.support.multidex.MultiDexApplication
import com.fasterxml.jackson.databind.ObjectMapper
import io.dotcoding.software.restaurante.BuildConfig
import io.dotcoding.software.restaurante.network.NetworkInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class RApplication : MultiDexApplication() {

    companion object {

        lateinit var instance: RApplication
            private set
        lateinit var retrofit: Retrofit
        fun apiInterface(): NetworkInterface {
            return retrofit.create(NetworkInterface::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.readTimeout(60, TimeUnit.SECONDS)
        clientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(60, TimeUnit.SECONDS)
        clientBuilder.addInterceptor { chain ->
            // get original request
            val original = chain.request()
            // build a new request with edited url and go further
            val newRequest = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()
            return@addInterceptor chain.proceed(newRequest)
        }
        val objectMapper = ObjectMapper()
        val dateFromat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ", Locale.getDefault())
        objectMapper.dateFormat = dateFromat
        val client = clientBuilder.build()
        retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build()
    }
}