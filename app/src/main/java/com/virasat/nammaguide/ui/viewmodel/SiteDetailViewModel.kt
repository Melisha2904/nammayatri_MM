package com.virasat.nammaguide.ui.viewmodel

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.virasat.nammaguide.data.db.VirastatDatabase
import com.virasat.nammaguide.data.model.CheckIn
import com.virasat.nammaguide.data.model.HeritageSite
import com.virasat.nammaguide.data.repository.CheckInRepository
import com.virasat.nammaguide.data.repository.FavoriteRepository
import com.virasat.nammaguide.data.repository.HeritageSiteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SiteDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val db = VirastatDatabase.getDatabase(application)
    private val checkInRepo = CheckInRepository(db.checkInDao())
    private val favoriteRepo = FavoriteRepository(db.favoriteDao())

    // Currently viewed site
    private val _site = MutableStateFlow<HeritageSite?>(null)
    val site: StateFlow<HeritageSite?> = _site.asStateFlow()

    // Language toggle (true = Kannada)
    private val _showKannada = MutableStateFlow(false)
    val showKannada: StateFlow<Boolean> = _showKannada.asStateFlow()

    // Check-in status for current site
    val isCheckedIn: StateFlow<Boolean> = _site
        .filterNotNull()
        .flatMapLatest { checkInRepo.isCheckedIn(it.id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // Favorite status
    val isFavorite: StateFlow<Boolean> = _site
        .filterNotNull()
        .flatMapLatest { favoriteRepo.isFavorite(it.id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // Audio playback
    private var mediaPlayer: MediaPlayer? = null
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _audioProgress = MutableStateFlow(0f)
    val audioProgress: StateFlow<Float> = _audioProgress.asStateFlow()

    // Hidden fact unlocked via QR
    private val _hiddenFactUnlocked = MutableStateFlow(false)
    val hiddenFactUnlocked: StateFlow<Boolean> = _hiddenFactUnlocked.asStateFlow()

    private var progressJob: Job? = null

    fun loadSite(siteId: String) {
        _site.value = HeritageSiteRepository.findById(siteId)
        _hiddenFactUnlocked.value = false
    }

    fun toggleLanguage() {
        _showKannada.value = !_showKannada.value
    }

    fun toggleFavorite() {
        val s = _site.value ?: return
        viewModelScope.launch {
            if (isFavorite.value) {
                favoriteRepo.removeFavorite(s.id)
            } else {
                favoriteRepo.addFavorite(s.id)
            }
        }
    }

    fun toggleCheckIn() {
        val s = _site.value ?: return
        viewModelScope.launch {
            if (isCheckedIn.value) {
                checkInRepo.checkOut(CheckIn(siteId = s.id, siteName = s.nameEn, location = s.location))
            } else {
                checkInRepo.checkIn(CheckIn(siteId = s.id, siteName = s.nameEn, location = s.location))
            }
        }
    }

    fun unlockHiddenFact() {
        _hiddenFactUnlocked.value = true
    }

    fun toggleAudio(rawResId: Int) {
        if (_isPlaying.value) {
            pauseAudio()
        } else {
            playAudio(rawResId)
        }
    }

    private fun playAudio(rawResId: Int) {
        if (mediaPlayer == null) {
            if (rawResId == 0) {
                _isPlaying.value = true
                startProgressTracking(isSimulated = true)
                return
            }
            mediaPlayer = MediaPlayer.create(getApplication(), rawResId)
            mediaPlayer?.setOnCompletionListener {
                _isPlaying.value = false
                _audioProgress.value = 0f
                progressJob?.cancel()
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }
        mediaPlayer?.start()
        _isPlaying.value = true
        startProgressTracking(isSimulated = false)
    }

    private fun startProgressTracking(isSimulated: Boolean) {
        progressJob?.cancel()
        progressJob = viewModelScope.launch {
            while (_isPlaying.value) {
                if (isSimulated) {
                    val nextProgress = _audioProgress.value + 0.01f
                    if (nextProgress >= 1f) {
                        _audioProgress.value = 0f
                        _isPlaying.value = false
                    } else {
                        _audioProgress.value = nextProgress
                    }
                } else {
                    mediaPlayer?.let { mp ->
                        if (mp.isPlaying && mp.duration > 0) {
                            _audioProgress.value = mp.currentPosition.toFloat() / mp.duration.toFloat()
                        }
                    }
                }
                delay(500)
            }
        }
    }

    fun pauseAudio() {
        mediaPlayer?.pause()
        _isPlaying.value = false
        progressJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        progressJob?.cancel()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
