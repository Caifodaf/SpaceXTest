package ru.android.spacextest.ui.main

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.android.spacextest.api.Repository
import ru.android.spacextest.models.*
import ru.android.spacextest.ui.main.adapters.LaunchersAdapter
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    companion object{
        const val ERROR_LOAD = 0
        const val ERROR_MORE_LOAD = 1
    }

    var page:Int = 1
    var totalPage:Int = 1
    private lateinit var job: Job

    lateinit var launchersAdapter: LaunchersAdapter

    var allMutableListLaunchers = mutableListOf<LaunchesModel>()


    private val _listLaunches = MutableLiveData<List<LaunchesModel>>()
    val listLaunches: LiveData<List<LaunchesModel>>
        get() = _listLaunches

    private val _errorUpdate = MutableLiveData<List<ErrorMessage>>()
    val errorUpdate: LiveData<List<ErrorMessage>>
        get() = _errorUpdate

    init {
        getLaunchesList()
    }

    fun initRecyclerView(rvList: RecyclerView, activity: MainFragment) {
        launchersAdapter = LaunchersAdapter(activity)
        rvList.adapter = launchersAdapter
    }

    fun getLaunchesList() {
        val body = BodyLaunchersModel(
            options= BodyOptionsLaunchersModel(
                page = page
            )
        )
        job = viewModelScope.launch(Dispatchers.IO) {
            repository.getLaunchesList(body)
                .catch { exception ->
                    Log.e("ErrorApi", exception.toString())
                    page = 0
                    _errorUpdate.postValue(listOf(ErrorMessage(
                        type = ERROR_LOAD,
                        code = exception.stackTrace[0].methodName,
                        message = exception.localizedMessage?.toString() ?: ""
                    )))
                }
                .collect {
                    page += 1
                    totalPage = it!!.totalPages
                    _listLaunches.postValue(it.docs.requireNoNulls())
                    for(i in 0 until it.docs.size)
                        allMutableListLaunchers.add(it.docs[i])
                }
        }
    }

    fun loadMoreData() {
        val body = BodyLaunchersModel(
            options= BodyOptionsLaunchersModel(
                page = page
            )
        )
        if (!listLaunches.value.isNullOrEmpty()){
            job = viewModelScope.launch(Dispatchers.IO) {
                repository.getLaunchesList(body)
                    .catch { exception ->
                        Log.e("ErrorApi", exception.toString())
                        page = 0
                        _errorUpdate.postValue(listOf(ErrorMessage(
                            type = ERROR_MORE_LOAD,
                            code = exception.stackTrace[0].methodName,
                            message = exception.localizedMessage?.toString() ?: ""
                        )))
                    }
                    .collect {
                        page += 1
                        _listLaunches.postValue(it!!.docs.requireNoNulls())
                        for(i in 0 until it.docs.size)
                            allMutableListLaunchers.add(it.docs[i])
                    }
            }
        }
    }

    override fun onCleared() {
        if(::job.isInitialized) job.cancel()
        super.onCleared()
    }

}