package com.virasat.nammaguide.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.virasat.nammaguide.ui.theme.TempleGold
import com.virasat.nammaguide.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    vm: SettingsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val notificationsEnabled by vm.notificationsEnabled.collectAsState()
    val locationSharing by vm.locationSharing.collectAsState()
    val darkMode by vm.darkMode.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", color = TempleGold, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TempleGold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Preferences",
                style = MaterialTheme.typography.titleMedium,
                color = TempleGold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            SettingsToggleItem(
                label = "Push Notifications",
                description = "Receive updates about nearby heritage sites",
                checked = notificationsEnabled,
                onCheckedChange = vm::setNotificationsEnabled
            )

            SettingsToggleItem(
                label = "Location Sharing",
                description = "Show relevant sites based on your current position",
                checked = locationSharing,
                onCheckedChange = vm::setLocationSharing
            )

            SettingsToggleItem(
                label = "Dark Mode",
                description = "Toggle between light and dark themes",
                checked = darkMode,
                onCheckedChange = vm::setDarkMode
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "Account",
                style = MaterialTheme.typography.titleMedium,
                color = TempleGold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            SettingsActionItem(label = "Edit Profile", icon = Icons.Filled.Edit)
            SettingsActionItem(label = "Language Preferences", icon = Icons.Filled.Language)
            SettingsActionItem(label = "Privacy Policy", icon = Icons.Filled.PrivacyTip)

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "App Information",
                style = MaterialTheme.typography.titleMedium,
                color = TempleGold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            SettingsActionItem(label = "About Virasat", icon = Icons.Filled.Info)
            SettingsActionItem(label = "Version", value = "1.0.0 (Stable)")
        }
    }
}

@Composable
fun SettingsToggleItem(
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodyLarge)
            Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = TempleGold)
        )
    }
}

@Composable
fun SettingsActionItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    value: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.width(16.dp))
        }
        Text(label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        if (value != null) {
            Text(value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        } else {
            Icon(Icons.Filled.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
