package ru.android.spacextest.ui.page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.android.spacextest.api.Repository
import ru.android.spacextest.models.CrewsModel
import ru.android.spacextest.models.ErrorMessage
import ru.android.spacextest.models.LaunchesModel
import ru.android.spacextest.ui.main.adapters.LaunchersAdapter
import javax.inject.Inject

@HiltViewModel
class PageViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var idsCrews:List<String> = listOf()
    private lateinit var job: Job

    private val _listCrews = MutableLiveData<List<CrewsModel>>()
    val listCrews: LiveData<List<CrewsModel>>
    get() = _listCrews

    private val _errorUpdate = MutableLiveData<List<ErrorMessage>>()
    val errorUpdate: LiveData<List<ErrorMessage>>
        get() = _errorUpdate

    init {
        getCrewList()
    }

    private fun getCrewList() {
        job = viewModelScope.launch(Dispatchers.IO) {
            repository.getCrewList()
                .map { listCrews ->
                    val list: MutableList<CrewsModel> = mutableListOf()
                    for (element in idsCrews) {
                        list += listCrews.filter {
                            it.id == element
                        }.toMutableList()
                    }
                    list
                }
                .catch { exception ->
                    Log.e("ErrorApi", exception.message.toString())
                    _errorUpdate.value = listOf(ErrorMessage(
                        code = exception.stackTrace[0].methodName,
                        message = exception.localizedMessage?.toString() ?: ""
                    ))
                }
                .collect {
                    _listCrews.postValue(it.requireNoNulls())
                }
        }
    }

    override fun onCleared() {
        if(::job.isInitialized) job.cancel()
        super.onCleared()
    }
}