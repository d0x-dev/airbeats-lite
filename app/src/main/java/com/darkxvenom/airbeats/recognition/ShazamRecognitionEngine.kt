package com.darkxvenom.airbeats.recognition

import android.content.Context
import com.darkxvenom.airbeats.utils.GlobalLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShazamRecognitionEngine(
    private val context: Context,
) : MusicRecognitionEngine {

    companion object {
        private const val TAG = "ShazamEngine"
    }

    override val providerName: String = "Shazam"

    override suspend fun recognize(audioSource: AudioSource): RecognitionResult = withContext(Dispatchers.IO) {
        GlobalLog.append(android.util.Log.INFO, TAG, "Shazam native engine not available in open edition, falling back")
        RecognitionResult(success = false)
    }
}
