package com.jspako.caloriepal.ui.nutrition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NutritionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Nutrition Page"
    }
    val text: LiveData<String> = _text
}