package com.example.qrcraft.scanner.di

import com.example.qrcraft.scanner.data.database.RoomScannerDataSource
import com.example.qrcraft.scanner.domain.ScannerDataSource
import com.example.qrcraft.scanner.presentation.qr_form.QrCodeFormViewModel
import com.example.qrcraft.scanner.presentation.scanner.ScannerViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val scannerModule = module {
    singleOf(::RoomScannerDataSource) bind ScannerDataSource::class

    viewModelOf(::ScannerViewModel)
    viewModelOf(::QrCodeFormViewModel)
}