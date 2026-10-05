package com.darkxvenom.airbeats.utils

object RemoteConfigManager {
    const val listenTogetherUrl: String = "https://listen-together.airbeats.workers.dev"

    fun getListenTogetherUrl(): String = listenTogetherUrl

    fun getSongShareUrl(songId: String): String {
        return "https://music.youtube.com/watch?v=$songId"
    }
}
