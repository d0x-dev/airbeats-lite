package com.darkxvenom.airbeats.media

data class MediaInfo(
    val durationMs: Long = 0L,
    val audioTrackIndex: Int = -1,
    val mimeType: String? = null,
)
