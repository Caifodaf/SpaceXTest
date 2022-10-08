package ru.android.spacextest.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.android.spacextest.api.init.RetrofitClient
import ru.android.spacextest.models.BodyLaunchersModel
import ru.android.spacextest.models.CrewsModel
import ru.android.spacextest.models.LaunchesModel
import ru.android.spacextest.models.LaunchesModelResponse
import javax.inject.Inject

class Repository @Inject constructor(){

    suspend fun getLaunchesList(body: BodyLaunchersModel): Flow<LaunchesModelResponse?> {
        return flow {
            emit(RetrofitClient.api.getLaunchesList(body).body()!!)
        }
    }

    suspend fun getCrewList(): Flow<List<CrewsModel>>{
        return flow {
            emit(RetrofitClient.api.getCrewList().body()!!)
        }
    }
}