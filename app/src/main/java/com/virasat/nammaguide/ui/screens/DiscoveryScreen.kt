package com.virasat.nammaguide.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.virasat.nammaguide.data.model.HeritageSite
import com.virasat.nammaguide.data.model.SiteCategory
import com.virasat.nammaguide.ui.theme.*
import com.virasat.nammaguide.ui.viewmodel.DiscoveryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    onSiteClick: (String) -> Unit,
    onScanClick: () -> Unit,
    onPassportClick: () -> Unit,
    onDashboardClick: () -> Unit,
    vm: DiscoveryViewModel = viewModel()
) {
    val sites by vm.nearbySites.collectAsState()
    val query by vm.searchQuery.collectAsState()
    val radius by vm.selectedRadius.collectAsState()
    val selectedCategory by vm.selectedCategory.collectAsState()
    val checkinCount by vm.totalCheckins.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("ವಿರಾಸತ್ • Virasat", style = MaterialTheme.typography.headlineSmall,
                            color = TempleGold, fontWeight = FontWeight.Bold)
                        Text("Karnataka Heritage Explorer", style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                actions = {
                    IconButton(onClick = onPassportClick) {
                        BadgedBox(badge = {
                            if (checkinCount > 0) Badge { Text("$checkinCount") }
                        }) {
                            Icon(Icons.Filled.CardTravel, "Travel Passport", tint = TempleGold)
                        }
                    }
                    IconButton(onClick = onScanClick) {
                        Icon(Icons.Filled.QrCodeScanner, "Scan QR", tint = WarmSaffron)
                    }
                    IconButton(onClick = onDashboardClick) {
                        Icon(Icons.Filled.AccountCircle, "User Dashboard", tint = TempleGold)
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
            item {
                OutlinedTextField(
                    value = query,
                    onValueChange = vm::setSearchQuery,
                    placeholder = { Text("Search temples, inscriptions…") },
                    leadingIcon = { Icon(Icons.Filled.Search, null, tint = TempleGold) },
                    trailingIcon = {
                        if (query.isNotEmpty()) IconButton(onClick = { vm.setSearchQuery("") }) {
                            Icon(Icons.Filled.Clear, null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TempleGold,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    ),
                    singleLine = true
                )
            }

            item {
                Column {
                    Text("Categories", style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(SiteCategory.entries) { cat ->
                            FilterChip(
                                selected = selectedCategory == cat,
                                onClick = { vm.setCategory(cat) },
                                label = { Text(cat.name.lowercase().capitalize()) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = TempleGold,
                                    selectedLabelColor = DarkBrown
                                )
                            )
                        }
                    }
                }
            }

            item {
                Column {
                    Text("Sites within radius", style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(listOf(10.0, 25.0, 50.0, 100.0, 500.0)) { km ->
                            FilterChip(
                                selected = radius == km,
                                onClick = { vm.setRadius(km) },
                                label = { Text("${km.toInt()} km") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = TempleGold,
                                    selectedLabelColor = DarkBrown
                                )
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${sites.size} Heritage Sites Found",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold)
                    Text("✦", color = TempleGold)
                }
            }

            items(sites, key = { it.id }) { site ->
                SiteCard(site = site, onClick = { onSiteClick(site.id) })
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun SiteCard(site: HeritageSite, onClick: () -> Unit) {
    val categoryColor = when (site.category) {
        SiteCategory.TEMPLE     -> WarmSaffron
        SiteCategory.INSCRIPTION -> RoyalIndigo
        SiteCategory.FORT       -> MossGreen
        SiteCategory.STEP_WELL  -> InfoBlue
        SiteCategory.CAVE       -> StoneBrown
        SiteCategory.MONUMENT   -> KannadaRed
    }
    val categoryIcon = when (site.category) {
        SiteCategory.TEMPLE     -> "🛕"
        SiteCategory.INSCRIPTION -> "📜"
        SiteCategory.FORT       -> "🏰"
        SiteCategory.STEP_WELL  -> "💧"
        SiteCategory.CAVE       -> "🪨"
        SiteCategory.MONUMENT   -> "🗿"
    }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(
                        Brush.horizontalGradient(listOf(DeepTerracotta, TempleGold, WarmSaffron))
                    )
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = categoryColor.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "$categoryIcon ${site.category.name.replace('_', ' ')}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = categoryColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Surface(
                        shape = CircleShape,
                        color = TempleGold.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.LocationOn, null, tint = TempleGold,
                                modifier = Modifier.size(12.dp))
                            Spacer(Modifier.width(2.dp))
                            Text("${site.distanceKm} km", style = MaterialTheme.typography.labelSmall,
                                color = TempleGold)
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Text(site.nameEn, style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                Text(site.nameKn, style = MaterialTheme.typography.bodyMedium,
                    color = TempleGold.copy(alpha = 0.8f))

                Spacer(Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Place, null, tint = WarmSaffron, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(site.location, style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Spacer(Modifier.height(8.dp))

                Text(site.shortDescEn, style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2)

                Spacer(Modifier.height(10.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(site.tags.take(4)) { tag ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Text(
                                text = "#$tag",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = WarmSaffron
                            )
                        }
                    }
                }
            }
        }
    }
}
