package com.virasat.nammaguide.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.virasat.nammaguide.data.repository.SettingsRepository
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SettingsRepository(application)

    val notificationsEnabled: StateFlow<Boolean> = repository.notificationsEnabled
    val locationSharing: StateFlow<Boolean> = repository.locationSharing
    val darkMode: StateFlow<Boolean> = repository.darkMode

    fun setNotificationsEnabled(enabled: Boolean) {
        repository.setNotificationsEnabled(enabled)
    }

    fun setLocationSharing(enabled: Boolean) {
        repository.setLocationSharing(enabled)
    }

    fun setDarkMode(enabled: Boolean) {
        repository.setDarkMode(enabled)
    }
}
