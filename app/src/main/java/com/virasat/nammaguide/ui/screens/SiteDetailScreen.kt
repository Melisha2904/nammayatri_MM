package com.virasat.nammaguide.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.virasat.nammaguide.ui.theme.*
import com.virasat.nammaguide.ui.viewmodel.SiteDetailViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SiteDetailScreen(
    siteId: String,
    onBack: () -> Unit,
    onScanQr: () -> Unit,
    vm: SiteDetailViewModel = viewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(siteId) { vm.loadSite(siteId) }

    val site by vm.site.collectAsState()
    val showKannada by vm.showKannada.collectAsState()
    val isCheckedIn by vm.isCheckedIn.collectAsState()
    val isFavorite by vm.isFavorite.collectAsState()
    val isPlaying by vm.isPlaying.collectAsState()
    val audioProgress by vm.audioProgress.collectAsState()
    val hiddenFactUnlocked by vm.hiddenFactUnlocked.collectAsState()

    if (site == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = TempleGold)
        }
        return
    }

    val s = site!!

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text(if (showKannada) s.nameKn else s.nameEn,
                    style = MaterialTheme.typography.headlineSmall,
                    color = TempleGold, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TempleGold)
                    }
                },
                actions = {
                    // Favorite Toggle
                    IconButton(onClick = vm::toggleFavorite) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) KannadaRed else TempleGold
                        )
                    }
                    // Language toggle
                    TextButton(onClick = vm::toggleLanguage) {
                        Text(if (showKannada) "EN" else "ಕ", color = WarmSaffron,
                            fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                // Map Directions FAB
                SmallFloatingActionButton(
                    onClick = {
                        val gmmIntentUri = Uri.parse("google.navigation:q=${s.latitude},${s.longitude}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        try {
                            context.startActivity(mapIntent)
                        } catch (e: Exception) {
                            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${s.latitude},${s.longitude}"))
                            context.startActivity(webIntent)
                        }
                    },
                    containerColor = WarmSaffron,
                    contentColor = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(Icons.Filled.Map, "Get Directions")
                }

                ExtendedFloatingActionButton(
                    onClick = vm::toggleCheckIn,
                    containerColor = if (isCheckedIn) CheckInGreen else TempleGold,
                    contentColor = DarkBrown,
                    icon = {
                        Icon(if (isCheckedIn) Icons.Filled.CheckCircle else Icons.Filled.AddLocationAlt, null)
                    },
                    text = { Text(if (isCheckedIn) "Checked In ✓" else "Check In",
                        fontWeight = FontWeight.Bold) }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(DeepTerracotta, StoneBrown, DarkBrown)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (s.imageResId != 0) {
                    androidx.compose.foundation.Image(
                        painter = androidx.compose.ui.res.painterResource(s.imageResId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                    // Gradient overlay for text readability
                    Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)))))
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = when (s.category.name) {
                            "TEMPLE"      -> "🛕"
                            "INSCRIPTION" -> "📜"
                            "CAVE"        -> "🪨"
                            "STEP_WELL"   -> "💧"
                            "FORT"        -> "🏰"
                            else          -> "🗿"
                        },
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(s.era, style = MaterialTheme.typography.bodyLarge,
                        color = AncientIvory.copy(alpha = 0.9f), fontWeight = FontWeight.Bold)
                    Text(if (showKannada) s.dynastyKn else s.dynastyEn,
                        style = MaterialTheme.typography.titleMedium, color = TempleGold)
                }
            }

            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                
                // Title and Tagline
                Column {
                    Text(
                        text = if (showKannada) s.nameKn else s.nameEn,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = if (showKannada) s.shortDescKn else s.shortDescEn,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TempleGold,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }

                // Location row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.LocationOn, null, tint = WarmSaffron, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(s.location, style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.weight(1f))
                    Surface(shape = RoundedCornerShape(8.dp), color = TempleGold.copy(alpha = 0.15f)) {
                        Text("${s.distanceKm} km away",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall, color = TempleGold)
                    }
                }

                // Audio Guide Card
                AudioGuideCard(
                    isPlaying = isPlaying, 
                    progress = audioProgress,
                    onToggle = { vm.toggleAudio(s.audioResId) }
                )

                // Tags section
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    s.tags.forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, TempleGold.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = "#$tag",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = TempleGold
                            )
                        }
                    }
                }

                // Ornamental divider
                OrnamentalDivider()

                // Full Description
                SectionCard(title = if (showKannada) "ಇತಿಹಾಸ" else "History") {
                    Text(
                        text = if (showKannada) s.fullDescKn else s.fullDescEn,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                    )
                }

                // Architectural Significance
                SectionCard(title = if (showKannada) "ವಾಸ್ತುಶಿಲ್ಪ ಮಹತ್ವ" else "Architectural Significance") {
                    Text(s.architecturalSignificance,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface)
                }

                // Local Legend
                SectionCard(
                    title = if (showKannada) "ಸ್ಥಳೀಯ ದಂತಕಥೆ" else "Local Legend",
                    accentColor = WarmSaffron
                ) {
                    Row {
                        Text("🪔 ", style = MaterialTheme.typography.bodyLarge)
                        Text(s.localLegend,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                    }
                }

                // QR Hidden Fact
                HiddenFactCard(
                    unlocked = hiddenFactUnlocked,
                    fact = s.hiddenFact,
                    onScanQr = onScanQr
                )

                Spacer(Modifier.height(80.dp)) // FAB clearance
            }
        }
    }
}

@Composable
fun AudioGuideCard(isPlaying: Boolean, progress: Float, onToggle: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = StoneBrown.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("🎙 Audio Guide", style = MaterialTheme.typography.titleMedium,
                        color = AncientIvory, fontWeight = FontWeight.Bold)
                    Text(if (isPlaying) "Playing…" else "Tap to listen",
                        style = MaterialTheme.typography.labelSmall,
                        color = AncientIvory.copy(alpha = 0.7f))
                }
                FilledIconButton(
                    onClick = onToggle,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = TempleGold)
                ) {
                    Icon(
                        if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        null, tint = DarkBrown
                    )
                }
            }
            if (isPlaying) {
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                    color = TempleGold,
                    trackColor = AncientIvory.copy(alpha = 0.2f)
                )
            }
        }
    }
}

@Composable
fun HiddenFactCard(unlocked: Boolean, fact: String, onScanQr: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, TempleGold),
        colors = CardDefaults.cardColors(
            containerColor = if (unlocked) TempleGold.copy(alpha = 0.1f)
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🔐", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = if (unlocked) "Hidden Fact Unlocked!" else "Hidden Fact",
                    style = MaterialTheme.typography.titleMedium,
                    color = TempleGold, fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(10.dp))
            AnimatedVisibility(visible = unlocked) {
                Text(fact, style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface)
            }
            AnimatedVisibility(visible = !unlocked) {
                Button(
                    onClick = onScanQr,
                    colors = ButtonDefaults.buttonColors(containerColor = DeepTerracotta),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.QrCodeScanner, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Scan QR to Unlock")
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    accentColor: Color = TempleGold,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(4.dp).height(22.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(accentColor)
            )
            Spacer(Modifier.width(8.dp))
            Text(title, style = MaterialTheme.typography.titleMedium,
                color = accentColor, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(8.dp))
        content()
    }
}

@Composable
fun OrnamentalDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = TempleGold.copy(alpha = 0.3f))
        Text("  ✦  ", color = TempleGold.copy(alpha = 0.7f))
        HorizontalDivider(modifier = Modifier.weight(1f), color = TempleGold.copy(alpha = 0.3f))
    }
}
