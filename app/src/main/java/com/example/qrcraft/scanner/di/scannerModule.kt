package com.example.qrcraft.scanner.di

import com.example.qrcraft.scanner.presentation.scanner.ScannerViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val scannerModule = module {
    viewModelOf(::ScannerViewModel)
}