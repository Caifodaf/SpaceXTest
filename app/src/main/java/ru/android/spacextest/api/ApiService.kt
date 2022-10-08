package ru.android.spacextest.api

import retrofit2.Response
import retrofit2.http.*
import ru.android.spacextest.models.*

interface ApiService {

    @POST("launches/query")
    @Headers("Content-Type: application/json")
    suspend fun getLaunchesList(@Body body:BodyLaunchersModel): Response<LaunchesModelResponse>

    @GET("crew")
    @Headers("Content-Type: application/json")
    suspend fun getCrewList(): Response<List<CrewsModel>>


}