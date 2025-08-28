package com.example.qrcraft.app

import android.app.Application
import com.example.qrcraft.scanner.di.grFormModule
import com.example.qrcraft.scanner.di.scannerModule
import org.koin.core.context.GlobalContext.startKoin

class QrCraftApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                scannerModule, grFormModule
            )
        }
    }
}