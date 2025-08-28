package com.example.qrcraft.scanner.di

import com.example.qrcraft.scanner.presentation.qr_form.QrCodeFormViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val grFormModule = module {
    viewModelOf(::QrCodeFormViewModel)
}