package com.example.myapplication

import android.content.Intent
import android.net.Uri
import java.net.URLEncoder

actual fun openYoutubeSearch(query: String) {
    val encodedQuery = URLEncoder.encode(query, "UTF-8")
    val youtubeUrl = "https://www.youtube.com/results?search_query=$encodedQuery"
    val app = MyApplicationHolder.application ?: return

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    app.startActivity(intent)
}

actual fun openUrl(url: String) {
    val app = MyApplicationHolder.application ?: return

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    app.startActivity(intent)
}
