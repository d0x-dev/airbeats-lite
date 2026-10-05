package com.darkxvenom.airbeats.media

import android.content.Context
import java.io.File

class TemporaryMediaManager(private val context: Context) {
    fun createTempFile(suffix: String = "tmp", prefix: String = "airbeats_temp_"): File {
        val ext = if (suffix.startsWith(".")) suffix else ".$suffix"
        val dir = File(context.cacheDir, "temp_media").apply { if (!exists()) mkdirs() }
        return File.createTempFile(prefix, ext, dir)
    }

    fun cleanup(file: File?) {
        runCatching { file?.delete() }
    }
}
