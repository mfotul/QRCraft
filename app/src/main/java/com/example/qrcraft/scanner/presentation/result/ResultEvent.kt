package com.example.qrcraft.scanner.presentation.result

sealed interface ResultEvent {
    data object OnDownloadSucceeded : ResultEvent
}