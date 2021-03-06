package com.firebaseexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebaseexample.model.BaseRes
import com.firebaseexample.network.RetrofitInstance
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val resultsLiveData: MutableLiveData<BaseRes<String>> = MutableLiveData()

    init {
        getResultsFromAPI()
    }

    private fun getResultsFromAPI() {
        viewModelScope.launch {
            resultsLiveData.postValue(BaseRes.Loading())
            try {
                val response = RetrofitInstance.api.getData()
                if (response.isSuccessful) {
                    response.body()?.let { productIdResponse ->
                        resultsLiveData.postValue(BaseRes.Success(productIdResponse))
                    }
                } else {
                    resultsLiveData.postValue(BaseRes.Error(response.message()))
                }
            } catch (throwableError: Throwable) {
                throwableError.localizedMessage?.let {
                    resultsLiveData.postValue(BaseRes.Error(it))
                }
            }
        }
    }
}