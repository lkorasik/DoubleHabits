package com.lkorasik.doublehabits.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <T1, T2> LiveData<T1>.map(f: (T1) -> T2): LiveData<T2> = Transformations.map(this, f)
