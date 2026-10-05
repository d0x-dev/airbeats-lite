package com.darkxvenom.airbeats.songs

data class IdentifiedSong(
    val id: String = "",
    val title: String,
    val artist: String,
    val album: String? = null,
    val albumArtUrl: String? = null,
    val durationMs: Long? = null,
    val provider: String? = null,
    val rawMetadata: Map<String, String> = emptyMap(),
)

object SongMetadataNormalizer {
    fun normalize(title: String): String {
        return title
            .replace(Regex("(?i)\\(official\\s*(video|audio|music\\s*video|lyric\\s*video|visualizer)?\\)"), "")
            .replace(Regex("(?i)\\[official\\s*(video|audio|music\\s*video|lyric\\s*video|visualizer)?\\]"), "")
            .replace(Regex("(?i)\\(lyrics?\\)"), "")
            .replace(Regex("(?i)\\[lyrics?\\]"), "")
            .replace(Regex("(?i)\\(audio\\)"), "")
            .replace(Regex("(?i)\\[audio\\]"), "")
            .replace(Regex("(?i)\\(ft\\.?.*?\\)"), "")
            .replace(Regex("(?i)\\(feat\\.?.*?\\)"), "")
            .trim()
    }
}
