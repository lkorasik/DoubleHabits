package com.lkorasik.doublehabits.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T1, T2> LiveData<T1>.addLiveData(liveData2: LiveData<T2>): LiveData<Pair<T1?, T2?>> {
    val mediator = MediatorLiveData<Pair<T1?, T2?>>()
    mediator.addSource(this) { mediator.value = it to liveData2.value }
    mediator.addSource(liveData2) { mediator.value = this.value to it }
    return mediator
}
