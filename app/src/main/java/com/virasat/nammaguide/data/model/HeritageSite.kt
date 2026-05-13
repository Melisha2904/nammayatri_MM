package com.virasat.nammaguide.data.model

/**
 * Represents a Heritage Site in Karnataka.
 * All data is simulated (no network call required).
 */
data class HeritageSite(
    val id: String,                      // Unique Site ID (matches QR code payload)
    val nameEn: String,                  // English name
    val nameKn: String,                  // Kannada name
    val location: String,               // District / Taluk
    val latitude: Double,
    val longitude: Double,
    val distanceKm: Double,             // Simulated distance from user
    val era: String,                    // e.g. "Hoysala – 12th Century"
    val dynastyEn: String,
    val dynastyKn: String,
    val shortDescEn: String,            // 1-sentence teaser
    val shortDescKn: String,
    val fullDescEn: String,             // Rich 3-4 paragraph history
    val fullDescKn: String,
    val architecturalSignificance: String,
    val localLegend: String,
    val hiddenFact: String,             // Unlocked via QR scan
    val tags: List<String>,             // e.g. ["Hoysala", "UNESCO", "Inscription"]
    val imageResId: Int = 0,            // Drawable resource ID (0 = placeholder)
    val audioResId: Int = 0,            // Raw audio resource ID (0 = none)
    val category: SiteCategory
)

enum class SiteCategory {
    TEMPLE, INSCRIPTION, FORT, STEP_WELL, CAVE, MONUMENT
}
