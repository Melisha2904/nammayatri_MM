package com.virasat.nammaguide.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.virasat.nammaguide.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {

    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 200f),
        label = "scale"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Reverse),
        label = "glow"
    )

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
        delay(2800)
        onFinished()
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(800),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(DarkBrown, StoneBrown, DarkBrown))
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alpha).scale(scale)
        ) {
            // Mandala / Symbol placeholder
            Text(
                text = "॥",
                fontSize = 80.sp,
                color = TempleGold.copy(alpha = glowAlpha),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "ವಿರಾಸತ್",
                style = MaterialTheme.typography.displayLarge,
                color = TempleGold,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Virasat",
                style = MaterialTheme.typography.headlineMedium,
                color = AncientIvory
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "ನಮ್ಮ ಗೈಡ್  •  Namma Guide",
                style = MaterialTheme.typography.titleMedium,
                color = WarmSaffron,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))
            // Ornamental divider
            Text(
                text = "✦ ──── Karnataka Heritage Explorer ──── ✦",
                style = MaterialTheme.typography.labelMedium,
                color = TempleGold.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}
