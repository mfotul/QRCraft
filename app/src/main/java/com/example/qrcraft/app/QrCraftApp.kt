package com.example.qrcraft.app

import android.app.Application
import com.example.qrcraft.core.database.di.databaseModule
import com.example.qrcraft.scanner.di.scannerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class QrCraftApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@QrCraftApp)
            modules(
                databaseModule,
                scannerModule,
            )
        }
    }
}