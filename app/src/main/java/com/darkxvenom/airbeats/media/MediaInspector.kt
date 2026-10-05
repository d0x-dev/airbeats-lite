package com.darkxvenom.airbeats.media

import android.content.Context
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri

object MediaInspector {
    fun inspect(context: Context, uri: Uri): MediaInfo {
        val extractor = MediaExtractor()
        return try {
            extractor.setDataSource(context, uri, null)
            var audioTrack = -1
            var durationUs = 0L
            var mime: String? = null
            for (i in 0 until extractor.trackCount) {
                val format = extractor.getTrackFormat(i)
                val trackMime = format.getString(MediaFormat.KEY_MIME) ?: ""
                if (trackMime.startsWith("audio/")) {
                    audioTrack = i
                    mime = trackMime
                    if (format.containsKey(MediaFormat.KEY_DURATION)) {
                        durationUs = format.getLong(MediaFormat.KEY_DURATION)
                    }
                    break
                }
            }
            MediaInfo(durationMs = durationUs / 1000L, audioTrackIndex = audioTrack, mimeType = mime)
        } catch (_: Exception) {
            MediaInfo()
        } finally {
            runCatching { extractor.release() }
        }
    }
}
