package com.virasat.nammaguide.data.repository

import com.virasat.nammaguide.data.db.FavoriteDao
import com.virasat.nammaguide.data.model.Favorite
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    val allFavorites: Flow<List<Favorite>> = favoriteDao.getAllFavorites()

    suspend fun addFavorite(siteId: String) {
        favoriteDao.addFavorite(Favorite(siteId))
    }

    suspend fun removeFavorite(siteId: String) {
        favoriteDao.removeFavorite(Favorite(siteId))
    }

    fun isFavorite(siteId: String): Flow<Boolean> {
        return favoriteDao.isFavorite(siteId)
    }
}
