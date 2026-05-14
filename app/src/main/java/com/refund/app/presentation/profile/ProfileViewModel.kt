package com.refund.app.presentation.profile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.refund.app.domain.repositories.ISubscriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ISubscriptionRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _notificationsEnabled = MutableLiveData<Boolean>(true)
    val notificationsEnabled: LiveData<Boolean> = _notificationsEnabled

    private val _totalSaved = MutableLiveData<Double>()
    val totalSaved: LiveData<Double> = _totalSaved

    init {
        loadUserData()
        calculateTotalSaved()
    }

    private fun loadUserData() {
        _userName.value = sharedPreferences.getString("user_name", "") ?: ""
        _notificationsEnabled.value = sharedPreferences.getBoolean("notifications_enabled", true)
    }

    fun setUserName(name: String) {
        sharedPreferences.edit().putString("user_name", name).apply()
        _userName.value = name
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("notifications_enabled", enabled).apply()
        _notificationsEnabled.value = enabled
    }

    private fun calculateTotalSaved() {
        // TODO: Вызвать use case для подсчета общей экономии
        // Пока заглушка
        _totalSaved.value = 0.0
    }

    fun refreshStatistics() {
        calculateTotalSaved()
    }
}
