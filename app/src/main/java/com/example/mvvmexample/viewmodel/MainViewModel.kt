package com.example.mvvmexample.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmexample.api.APIService
import com.example.mvvmexample.api.RetrofitHelper
import com.example.mvvmexample.model.InterestingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel : ViewModel() {
    val mainModel = MutableLiveData<MainModel>()

    @OptIn(DelicateCoroutinesApi::class)
    fun queryAnother() {
        val api = RetrofitHelper.getRetrofit().create(APIService::class.java);
        mainModel.value = MainModel(ready = false)
        GlobalScope.launch {
            try {
                val activity = api.getActivity()
                withContext(Dispatchers.Main) {
                    if (activity.isSuccessful) {
                        mainModel.value = MainModel(
                            ready = true,
                            activity = activity.body()
                        )
                    } else {
                        mainModel.value = MainModel(
                            ready = true,
                            errorMessage = "No se ha podido obtener la actividad"
                        )
                    }
                }
            } catch (ex: Exception) {
                Log.e("queryAnother", ex.toString())
                withContext(Dispatchers.Main) {
                    mainModel.value = MainModel(
                        ready = true,
                        errorMessage = "Ha ocurrido un error al comunicarse con el servidor"
                    )
                }
            }
        }
    }
}

data class MainModel(
    val ready: Boolean,
    val errorMessage: String = "",
    val activity: InterestingActivity? = null
)