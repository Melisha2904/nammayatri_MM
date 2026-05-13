package com.virasat.nammaguide.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.virasat.nammaguide.data.db.VirastatDatabase
import com.virasat.nammaguide.data.model.CheckIn
import com.virasat.nammaguide.data.model.HeritageSite
import com.virasat.nammaguide.data.model.SiteCategory
import com.virasat.nammaguide.data.repository.CheckInRepository
import com.virasat.nammaguide.data.repository.FavoriteRepository
import com.virasat.nammaguide.data.repository.HeritageSiteRepository
import kotlinx.coroutines.flow.*

class DiscoveryViewModel(application: Application) : AndroidViewModel(application) {

    private val db = VirastatDatabase.getDatabase(application)
    private val checkInRepo = CheckInRepository(db.checkInDao())
    private val favoriteRepo = FavoriteRepository(db.favoriteDao())

    val allCheckIns: StateFlow<List<CheckIn>> = checkInRepo.allCheckIns
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalCheckins: StateFlow<Int> = checkInRepo.totalCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _selectedRadius = MutableStateFlow(50.0)
    val selectedRadius: StateFlow<Double> = _selectedRadius.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<SiteCategory?>(null)
    val selectedCategory: StateFlow<SiteCategory?> = _selectedCategory.asStateFlow()

    val nearbySites: StateFlow<List<HeritageSite>> = combine(
        _selectedRadius, _searchQuery, _selectedCategory
    ) { radius, query, category ->
        HeritageSiteRepository.getNearbySites(radius).filter { site ->
            (query.isBlank() ||
            site.nameEn.contains(query, ignoreCase = true) ||
            site.nameKn.contains(query) ||
            site.location.contains(query, ignoreCase = true) ||
            site.tags.any { it.contains(query, ignoreCase = true) }) &&
            (category == null || site.category == category)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HeritageSiteRepository.allSites)

    val favoriteSites: StateFlow<List<HeritageSite>> = favoriteRepo.allFavorites
        .map { favs ->
            val favIds = favs.map { it.siteId }
            HeritageSiteRepository.allSites.filter { it.id in favIds }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setRadius(km: Double) { _selectedRadius.value = km }
    fun setSearchQuery(q: String) { _searchQuery.value = q }
    fun setCategory(cat: SiteCategory?) {
        _selectedCategory.value = if (_selectedCategory.value == cat) null else cat
    }
}
