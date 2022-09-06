package studio.eyesthetics.tetaarchitecturelesson.application.extensions

import android.content.Context

fun Context.readTextFromAsset(fileName : String) : String {
    return assets.open(fileName).bufferedReader().use {
        it.readText()
    }
}