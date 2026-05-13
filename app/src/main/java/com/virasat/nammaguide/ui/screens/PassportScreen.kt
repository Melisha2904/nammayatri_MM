package com.virasat.nammaguide.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.virasat.nammaguide.data.model.CheckIn
import com.virasat.nammaguide.ui.theme.*
import com.virasat.nammaguide.ui.viewmodel.DiscoveryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassportScreen(
    onBack: () -> Unit,
    vm: DiscoveryViewModel = viewModel()
) {
    val checkIns by vm.allCheckIns.collectAsState()
    val count by vm.totalCheckins.collectAsState()
    val totalSites = 6 // Total available sites

    val badgeLabel = when {
        count == 0    -> "Explorer"
        count <= 2    -> "Heritage Seeker"
        count <= 4    -> "Site Wanderer"
        count < totalSites -> "Heritage Scholar"
        else          -> "Karnataka Heritage Master 🏆"
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Travel Passport", color = TempleGold, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Back", tint = TempleGold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Passport Header Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(listOf(DeepTerracotta, StoneBrown))
                            )
                            .padding(24.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()) {

                            // Passport stamp icon
                            Surface(
                                shape = CircleShape,
                                color = TempleGold,
                                modifier = Modifier.size(72.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("🪬", style = MaterialTheme.typography.displayMedium)
                                }
                            }

                            Spacer(Modifier.height(12.dp))

                            Text("ಪ್ರವಾಸ ಪಾಸ್‌ಪೋರ್ಟ್",
                                style = MaterialTheme.typography.headlineSmall,
                                color = TempleGold, fontWeight = FontWeight.Bold)
                            Text("Heritage Travel Passport",
                                style = MaterialTheme.typography.bodyMedium, color = AncientIvory)

                            Spacer(Modifier.height(16.dp))

                            // Progress
                            Text("$count / $totalSites Sites Visited",
                                style = MaterialTheme.typography.titleLarge,
                                color = AncientIvory, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { if (totalSites > 0) count.toFloat() / totalSites else 0f },
                                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                                color = TempleGold,
                                trackColor = DarkBrown
                            )

                            Spacer(Modifier.height(12.dp))

                            // Badge
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = TempleGold.copy(alpha = 0.2f)
                            ) {
                                Text("⭐ $badgeLabel",
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TempleGold, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Section title
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Checklist, null, tint = TempleGold)
                    Spacer(Modifier.width(8.dp))
                    Text("Visited Sites",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold)
                }
            }

            if (checkIns.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🗺️", style = MaterialTheme.typography.displayMedium)
                            Spacer(Modifier.height(12.dp))
                            Text("No sites visited yet!",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Explore heritage sites and check in to stamp your passport.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
                        }
                    }
                }
            } else {
                itemsIndexed(checkIns) { index, checkIn ->
                    CheckInStamp(index = index + 1, checkIn = checkIn)
                }
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun CheckInStamp(index: Int, checkIn: CheckIn) {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    val dateStr = sdf.format(Date(checkIn.checkinTimestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Stamp number circle
            Surface(
                shape = CircleShape,
                color = TempleGold,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("$index", style = MaterialTheme.typography.titleMedium,
                        color = DarkBrown, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(checkIn.siteName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.LocationOn, null, tint = WarmSaffron,
                        modifier = Modifier.size(12.dp))
                    Spacer(Modifier.width(2.dp))
                    Text(checkIn.location, style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Schedule, null, tint = MutedText,
                        modifier = Modifier.size(12.dp))
                    Spacer(Modifier.width(2.dp))
                    Text(dateStr, style = MaterialTheme.typography.labelSmall,
                        color = MutedText)
                }
            }

            Icon(Icons.Filled.Verified, "Checked In", tint = CheckInGreen,
                modifier = Modifier.size(28.dp))
        }
    }
}
