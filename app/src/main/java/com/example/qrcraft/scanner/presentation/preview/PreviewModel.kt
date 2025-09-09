package com.example.qrcraft.scanner.presentation.preview

import com.example.qrcraft.scanner.domain.models.QrCodeData
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import com.example.qrcraft.scanner.presentation.models.QrCodeUi
import java.time.Instant

data object PreviewModel {
    val fakeQrCodes = listOf(
        QrCodeUi(
            id = 1,
            qrCodeData = QrCodeData.Link("https://www.google.com"),
            createdAt = Instant.now(),
            label = "Google Link",
            qrCodeSource = QrCodeSource.CREATED
        ),
        QrCodeUi(
            id = 2,
            qrCodeData = QrCodeData.Text("This is a longer piece of text intended to test how the QR code generation and display handles more extensive content. It includes multiple sentences and special characters like !@#\$%^&*()_+=-`~[]{};':\",./<>? to ensure robustness. The quick brown fox jumps over the lazy dog. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            createdAt = Instant.now().minusSeconds(3600), // Example: an hour ago
            label = "My QR Note",
            qrCodeSource = QrCodeSource.CREATED
        ),
        QrCodeUi(
            id = 3,
            qrCodeData = QrCodeData.Geo("35.6895", "139.6917"),
            createdAt = Instant.now().minusSeconds(7200), // Example: two hours ago
            label = "Tokyo Coordinates",
            qrCodeSource = QrCodeSource.SCANNED
        ),
        QrCodeUi(
            id = 4,
            qrCodeData = QrCodeData.Wifi("HomeNetwork", "securepass", "WPA2"),
            createdAt = Instant.now().minusSeconds(10800), // Example: three hours ago
            label = "Home Wi-Fi",
            qrCodeSource = QrCodeSource.CREATED
        ),
        QrCodeUi(
            id = 5,
            qrCodeData = QrCodeData.Contact(
                name = "Jane Roe",
                phone = "0987654321",
                email = "jane.roe@example-pet-store.com"
            ),
            createdAt = Instant.now().minusSeconds(14400), // Example: four hours ago
            label = "Jane R. Contact",
            qrCodeSource = QrCodeSource.SCANNED
        ),
        QrCodeUi(
            id = 6,
            qrCodeData = QrCodeData.Phone(number = "+1 555 123456"),
            createdAt = Instant.now().minusSeconds(10800), // Example: three hours ago
            label = "Home Wi-Fi",
            qrCodeSource = QrCodeSource.CREATED
        ),
        QrCodeUi(
            id = 10,
            qrCodeData = QrCodeData.Text("Another short text for variety."),
            createdAt = Instant.now().minusSeconds(28800), // Example: eight hours ago
            label = "Quick Note",
            qrCodeSource = QrCodeSource.CREATED
        ),
        QrCodeUi(
            id = 11,
            qrCodeData = QrCodeData.Link("https://developer.android.com"),
            createdAt = Instant.now().minusSeconds(32400), // Example: nine hours ago
            label = "Android Dev",
            qrCodeSource = QrCodeSource.SCANNED
        ),
        QrCodeUi(
            id = 12,
            qrCodeData = QrCodeData.Wifi("GuestNetwork", "guestpass", "WPA"),
            createdAt = Instant.now().minusSeconds(36000), // Example: ten hours ago
            label = "Guest Wi-Fi",
            qrCodeSource = QrCodeSource.CREATED
        ),
    )
}