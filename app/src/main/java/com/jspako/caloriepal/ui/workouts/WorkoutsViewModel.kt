package com.jspako.caloriepal.ui.workouts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkoutsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Workouts Page"
    }
    val text: LiveData<String> = _text
}