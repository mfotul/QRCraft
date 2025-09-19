package com.example.qrcraft.core.database.di

import androidx.room.Room
import com.example.qrcraft.core.database.MIGRATION_1_2
import com.example.qrcraft.core.database.QrCodeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single<QrCodeDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            QrCodeDatabase::class.java,
            "qr_code_database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
    single {
        get<QrCodeDatabase>().qrCodeDao
    }
}