package com.darkxvenom.airbeats.providers

import android.content.Context
import com.darkxvenom.airbeats.innertube.YouTube
import com.darkxvenom.airbeats.songs.IdentifiedSong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class ProviderSong(
    val id: String,
    val title: String,
    val artist: String,
    val durationSeconds: Int = 0,
    val thumbnailUrl: String? = null,
    val provider: String = "YouTube",
)

class ProviderSearchManager(private val context: Context) {
    suspend fun searchMatchingSongs(identifiedSong: IdentifiedSong): List<ProviderSong> = withContext(Dispatchers.IO) {
        val query = "${identifiedSong.title} ${identifiedSong.artist}".trim()
        if (query.isBlank()) return@withContext emptyList()
        try {
            val searchResult = YouTube.search(query).getOrNull() ?: return@withContext emptyList()
            searchResult.items.mapNotNull { item ->
                when (item) {
                    is com.darkxvenom.airbeats.innertube.models.SongItem -> ProviderSong(
                        id = item.id,
                        title = item.title,
                        artist = item.artists.joinToString(", ") { it.name },
                        durationSeconds = item.duration ?: 0,
                        thumbnailUrl = item.thumbnail?.url
                    )
                    else -> null
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    suspend fun searchProviders(identifiedSong: IdentifiedSong): List<ProviderSong> = searchMatchingSongs(identifiedSong)
}
