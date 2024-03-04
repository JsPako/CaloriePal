package com.jspako.caloriepal.ui.profile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private lateinit var sharedPreferences: SharedPreferences

    fun init(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
        // Update _text LiveData value after sharedPreferences has been initialized
        _text.value = sharedPreferences.getString("name", "Profile")
    }

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text
}