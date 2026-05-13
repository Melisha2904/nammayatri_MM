# ವಿರಾಸತ್ – Namma Guide 🛕
### Karnataka Heritage Explorer | Android App

> **"Turning every smartphone into a digital historian for Karnataka's hidden gems."**

---

## 📱 App Overview

**Virasat – Namma Guide** is a Smart Heritage Guide Android app that uses location-based discovery, rich bilingual storytelling, QR scanning (ML Kit), audio guides (MediaPlayer), and a Room DB–backed Travel Passport to bring Karnataka's forgotten historical sites to life.

---

## 🏗️ Architecture

```
app/
├── data/
│   ├── model/          # HeritageSite.kt, CheckIn.kt (Room Entity)
│   ├── db/             # VirastatDatabase.kt, CheckInDao.kt
│   └── repository/     # HeritageSiteRepository.kt, CheckInRepository.kt
├── ui/
│   ├── theme/          # Color.kt, Type.kt, Theme.kt
│   ├── navigation/     # NavGraph.kt
│   ├── screens/        # SplashScreen, DiscoveryScreen, SiteDetailScreen,
│   │                   # QrScannerScreen, PassportScreen
│   └── viewmodel/      # DiscoveryViewModel.kt, SiteDetailViewModel.kt
└── MainActivity.kt
```

**Pattern:** MVVM + Repository + Room + Jetpack Compose Navigation

---

## ✅ Success Criteria Implementation

| Criterion | Implementation |
|-----------|---------------|
| **QR Scanner** correctly identifies Site ID | ML Kit `BarcodeScanning` reads raw QR text → matched against `HeritageSiteRepository.findById()` |
| **Audio Guide** play/pause without crash | `MediaPlayer` lifecycle tied to `ViewModel.onCleared()` — always released on navigation |
| **Check-in persisted in Room DB** | `CheckIn` entity in `VirastatDatabase`, inserted/deleted via `CheckInDao` with `Flow`-based UI updates |
| **Temple architecture UI theme** | Terracotta + Temple Gold + Saffron palette, serif typography, ornamental dividers, gradient hero banners |

---

## 🗺️ Screens

### 1. Splash Screen
- Animated gold mandala symbol with scale spring animation
- Pulsing glow effect on `॥` Sanskrit symbol
- "ವಿರಾಸತ್ • Virasat" in bilingual display
- Auto-navigates after 2.8 seconds

### 2. Discovery Screen
- Search bar filtering by name / tags / location
- Radius filter chips: 10 / 25 / 50 / 100 km
- Rich site cards with: category badge, distance, Kannada name, description, tags
- QR Scanner FAB + Travel Passport icon with badge count

### 3. Site Detail Screen
- Gradient hero banner with era and dynasty
- **EN/ಕ toggle** for instant bilingual switch
- Audio Guide card with `MediaPlayer` play/pause
- History, Architectural Significance, Local Legend sections
- Hidden Fact card — locked until QR is scanned
- Check-in FAB (green when checked in)

### 4. QR Scanner Screen
- Live `CameraX` preview with `ImageAnalysis`
- ML Kit `BarcodeScanning` processes each frame
- Golden corner-bracket overlay as scan frame
- **Demo buttons** for 3 sites (no physical QR needed for testing)
- Error handling for unrecognised codes

### 5. Travel Passport Screen
- Progress bar: `visited / total` sites
- Dynamic badge: Explorer → Seeker → Wanderer → Scholar → **Master 🏆**
- Timestamped check-in stamps for every visited site
- Empty-state illustration when no check-ins exist

---

## 🏛️ Heritage Sites (Simulated Data)

| ID | Site | Era | Category |
|----|------|-----|----------|
| SITE_001 | Hoysaleswara Temple, Halebidu | 12th Century Hoysala | Temple |
| SITE_002 | Chennakeshava Temple, Belur | 1117 CE Hoysala | Temple |
| SITE_003 | Hire Benakal Megalithic Site | 1000–300 BCE Iron Age | Monument |
| SITE_004 | Aihole Rock Inscriptions | 634 CE Chalukya | Inscription |
| SITE_005 | Shivagange Step Wells | 9th Century Ganga | Step Well |
| SITE_006 | Badami Cave Temples | 6th–7th Century Chalukya | Cave |

---

## 🔧 Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Kotlin |
| UI Framework | Jetpack Compose + Material 3 |
| Navigation | Navigation Compose |
| Database | Room (with KSP) |
| QR Scanning | Google ML Kit Barcode |
| Camera | CameraX (Core, Camera2, Lifecycle, View) |
| Audio | Android MediaPlayer |
| Architecture | MVVM + Repository |
| DI | Manual (Application-level singletons) |
| Build | Gradle KTS + Version Catalog |

---

## 🚀 How to Build & Run

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- Android SDK 34
- JDK 17+
- Physical device or emulator with API 26+

### Steps
```bash
# Clone / open the project in Android Studio
# Sync Gradle (File → Sync Project with Gradle Files)
# Connect device or start emulator
# Run → Run 'app'  (Shift+F10)
```

### First Run Notes
- Grant **Camera** permission when prompted (required for QR scanner)
- Location permission is optional (distance is currently simulated)
- All heritage data is bundled locally — **no internet required**

---

## 📲 QR Code Setup (For Demo)

Generate QR codes encoding these exact strings:
- `SITE_001` → Hoysaleswara Temple
- `SITE_002` → Chennakeshava Temple  
- `SITE_003` → Hire Benakal
- `SITE_004` → Aihole Inscriptions
- `SITE_005` → Shivagange
- `SITE_006` → Badami Caves

Use [qr-code-generator.com](https://www.qr-code-generator.com/) → **Text** type.

> **Note:** The app also has tap-to-simulate buttons on the scanner screen for quick testing without printing QR codes.

---

## 🌐 Localization

- **English** → `res/values/strings.xml`
- **Kannada** → `res/values-kn/strings.xml`
- In-app toggle (EN / ಕ button) switches site content language instantly without restarting the app
- Device locale set to Kannada (`kn`) will automatically show Kannada app labels

---

## 🎨 Design Language

Inspired by **Hoysala temple architecture**:
- **Colors:** Temple Gold (#D4A017), Deep Terracotta (#8B2500), Warm Saffron (#E8732A), Ancient Ivory (#F5ECD7)
- **Typography:** Serif (HeritageSerif) for titles, Sans for body
- **Motifs:** Ornamental `✦` dividers, gradient header bars, golden corner brackets on scanner
- **Dark theme** default (resembling candlelit temple interiors)

---

## 📦 Dependencies

```toml
# Key versions (gradle/libs.versions.toml)
agp             = "8.5.2"
kotlin          = "2.0.0"
roomRuntime     = "2.6.1"
mlkitBarcode    = "17.3.0"
cameraX         = "1.3.4"
composeBom      = "2024.08.00"
navigationCompose = "2.7.7"
```

---

## 🏆 Impact Goals

| Goal | How App Addresses It |
|------|---------------------|
| **Cultural Preservation** | Digitises oral histories, local legends, and architectural descriptions of minor monuments |
| **Local Economy** | Discovery screen surfaces hidden gems near users → drives foot traffic to lesser-known villages |
| **Pride in Heritage** | Bilingual Kannada/English content educates the next generation in their mother tongue |
| **Gamification** | Travel Passport + badge system incentivises visiting more sites |

---

*Built for the Namma Yatri Karnataka Heritage GenAI Hackathon 2026*
