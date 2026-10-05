package com.darkxvenom.airbeats.share

import android.content.Context
import android.content.Intent
import android.net.Uri

enum class SharedContentType {
    URL,
    FILE,
    TEXT
}

data class SharedContent(
    val type: SharedContentType,
    val text: String? = null,
    val uri: Uri? = null,
    val mimeType: String? = null,
)

object ShareIntentParser {
    fun parse(context: Context, intent: Intent?): List<SharedContent> {
        if (intent == null) return emptyList()
        val results = mutableListOf<SharedContent>()
        val action = intent.action
        val type = intent.type ?: ""

        if (action == Intent.ACTION_SEND) {
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            val streamUri = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(Intent.EXTRA_STREAM) as? Uri
            }

            if (streamUri != null) {
                results.add(SharedContent(type = SharedContentType.FILE, uri = streamUri, mimeType = type))
            } else if (!text.isNullOrBlank()) {
                val isUrl = text.startsWith("http://") || text.startsWith("https://") ||
                        text.contains("instagram.com") || text.contains("tiktok.com") ||
                        text.contains("youtube.com") || text.contains("youtu.be")
                results.add(SharedContent(
                    type = if (isUrl) SharedContentType.URL else SharedContentType.TEXT,
                    text = text,
                    mimeType = type
                ))
            }
        }
        return results
    }
}
