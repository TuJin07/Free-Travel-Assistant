package com.cf.tourism

import android.net.Uri
import androidx.lifecycle.*

fun dataViewModel(lco: ViewModelStoreOwner): DataViewModel {
    return ViewModelProvider(lco).get(DataViewModel::class.java)
}

class DataViewModel: ViewModel() {
    private var allJourney : MutableLiveData<List<Journey>> = MutableLiveData()

    var uriLiveData: MutableLiveData<Uri> = MutableLiveData()

    fun addChangeCallback(
        cb : Observer<List<Journey>>
    ) {
        allJourney.observeForever(cb)
    }

    fun setValue(v: List<Journey>) {
        allJourney.value = v
    }

    override fun onCleared() {
        super.onCleared()
        allJourney.removeObserver {  }
    }
}