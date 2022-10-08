package ru.android.spacextest.api.init

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.android.spacextest.api.ApiService

object RetrofitClient {

    private const val BASE_URL = "https://api.spacexdata.com/v4/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .add(SerializeNullsFactory())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}