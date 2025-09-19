package com.example.qrcraft.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.qrcraft.core.database.qrcode.QrCodeDao
import com.example.qrcraft.core.database.qrcode.QrCodeEntity

@Database(
    entities = [QrCodeEntity::class],
    version = 2
)
abstract class QrCodeDatabase : RoomDatabase() {
    abstract val qrCodeDao: QrCodeDao
}
