package com.example.qrcraft.core.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE QrCodeEntity ADD COLUMN is_favorite INTEGER NOT NULL DEFAULT 0")
    }
}