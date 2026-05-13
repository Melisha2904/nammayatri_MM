package com.virasat.nammaguide.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.virasat.nammaguide.data.repository.HeritageSiteRepository
import com.virasat.nammaguide.ui.theme.*
import kotlinx.coroutines.delay
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScannerScreen(
    onSiteFound: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasCameraPermission = granted }

    var scannedCode by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isScanning by remember { mutableStateOf(true) }
    var isTorchOn by remember { mutableStateOf(false) }
    var scanSuccess by remember { mutableStateOf(false) }
    var showDemoPanel by remember { mutableStateOf(false) }
    var cameraControl by remember { mutableStateOf<CameraControl?>(null) }
    var cameraInfo by remember { mutableStateOf<CameraInfo?>(null) }

    // Auto-dismiss error
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            delay(3000)
            errorMessage = null
        }
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    // Haptic feedback on successful scan
    LaunchedEffect(scanSuccess) {
        if (scanSuccess) {
            try {
                val vibrator = context.getSystemService(Vibrator::class.java)
                vibrator?.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
            } catch (_: Exception) {}
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Scan QR Code", color = TempleGold, fontWeight = FontWeight.Bold)
                        Text("Point at a heritage site QR", color = AncientIvory.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.labelSmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Back", tint = TempleGold)
                    }
                },
                actions = {
                    // Torch toggle
                    IconButton(onClick = {
                        isTorchOn = !isTorchOn
                        cameraControl?.enableTorch(isTorchOn)
                    }) {
                        Icon(
                            if (isTorchOn) Icons.Filled.FlashOn else Icons.Filled.FlashOff,
                            "Toggle Flash",
                            tint = if (isTorchOn) TempleGold else AncientIvory.copy(alpha = 0.7f)
                        )
                    }
                    // Demo panel toggle
                    IconButton(onClick = { showDemoPanel = !showDemoPanel }) {
                        Icon(Icons.Filled.Science, "Demo Mode",
                            tint = if (showDemoPanel) TempleGold else AncientIvory.copy(alpha = 0.7f))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.7f)
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {

            if (hasCameraPermission) {
                // Enhanced camera preview with tap-to-focus
                EnhancedCameraPreview(
                    isTorchOn = isTorchOn,
                    onCameraBound = { control, info ->
                        cameraControl = control
                        cameraInfo = info
                    },
                    onQrScanned = { rawValue ->
                        if (isScanning) {
                            isScanning = false
                            scannedCode = rawValue
                            val matchedSite = HeritageSiteRepository.findById(rawValue)
                            if (matchedSite != null) {
                                scanSuccess = true
                                onSiteFound(matchedSite.id)
                            } else {
                                errorMessage = "Unknown code: \"$rawValue\""
                                isScanning = true
                            }
                        }
                    }
                )

                // Animated scanner overlay
                AnimatedScannerOverlay()

                // Instruction text at top
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        "Align QR code within the frame",
                        color = AncientIvory,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }

                // Bottom controls
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                            )
                        )
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Scanning indicator
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val pulseAlpha by rememberInfiniteTransition(label = "pulse").animateFloat(
                            initialValue = 0.4f, targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                tween(800), RepeatMode.Reverse
                            ), label = "alpha"
                        )
                        Canvas(modifier = Modifier.size(10.dp)) {
                            drawCircle(color = CheckInGreen.copy(alpha = pulseAlpha))
                        }
                        Spacer(Modifier.width(8.dp))
                        Text("Scanning actively…", color = CheckInGreen,
                            style = MaterialTheme.typography.labelMedium)
                    }

                    // Demo panel (collapsible)
                    AnimatedVisibility(visible = showDemoPanel) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("── Demo: Tap to simulate scan ──",
                                color = TempleGold.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.labelSmall)

                            HeritageSiteRepository.allSites.take(3).forEach { site ->
                                OutlinedButton(
                                    onClick = { onSiteFound(site.id) },
                                    modifier = Modifier.fillMaxWidth(),
                                    border = BorderStroke(1.dp, TempleGold.copy(alpha = 0.5f)),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TempleGold),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(Icons.Filled.QrCode, null, modifier = Modifier.size(14.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text(site.nameEn, style = MaterialTheme.typography.labelMedium)
                                }
                            }
                        }
                    }
                }

                // Error snackbar
                AnimatedVisibility(
                    visible = errorMessage != null,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 100.dp)
                ) {
                    errorMessage?.let { msg ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = AlertRed.copy(alpha = 0.9f)),
                            modifier = Modifier.padding(horizontal = 24.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Filled.ErrorOutline, null, tint = Color.White,
                                    modifier = Modifier.size(20.dp))
                                Spacer(Modifier.width(10.dp))
                                Text(msg, color = Color.White,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.weight(1f))
                                IconButton(onClick = { errorMessage = null },
                                    modifier = Modifier.size(24.dp)) {
                                    Icon(Icons.Filled.Close, null, tint = Color.White,
                                        modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            } else {
                // Permission denied state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = TempleGold.copy(alpha = 0.15f),
                        modifier = Modifier.size(120.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Filled.CameraAlt, null, tint = TempleGold,
                                modifier = Modifier.size(56.dp))
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                    Text("Camera Access Required", color = AncientIvory,
                        style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("We need camera access to scan QR codes\nat heritage sites",
                        color = AncientIvory.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center)
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                        colors = ButtonDefaults.buttonColors(containerColor = TempleGold),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Lock, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Grant Permission", color = DarkBrown, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun EnhancedCameraPreview(
    isTorchOn: Boolean,
    onCameraBound: (CameraControl, CameraInfo) -> Unit,
    onQrScanned: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val executor = remember { Executors.newSingleThreadExecutor() }

    // Configure barcode scanner for ALL barcode formats for maximum detection
    val options = remember {
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC,
                Barcode.FORMAT_DATA_MATRIX,
                Barcode.FORMAT_PDF417
            )
            .build()
    }
    val barcodeScanner = remember { BarcodeScanning.getClient(options) }

    // Track last scanned value to avoid rapid-fire duplicates
    var lastScannedValue by remember { mutableStateOf("") }
    var lastScannedTime by remember { mutableLongStateOf(0L) }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                scaleType = PreviewView.ScaleType.FILL_CENTER
                implementationMode = PreviewView.ImplementationMode.PERFORMANCE
            }

            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                // High-resolution preview
                val preview = Preview.Builder()
                    .setTargetResolution(android.util.Size(1920, 1080))
                    .build()
                    .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                // Optimized image analysis for fast scanning
                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(android.util.Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setOutputImageRotationEnabled(true)
                    .build()
                    .also { analysis ->
                        analysis.setAnalyzer(executor) { imageProxy ->
                            @androidx.camera.core.ExperimentalGetImage
                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                val inputImage = InputImage.fromMediaImage(
                                    mediaImage, imageProxy.imageInfo.rotationDegrees
                                )
                                barcodeScanner.process(inputImage)
                                    .addOnSuccessListener { barcodes ->
                                        val now = System.currentTimeMillis()
                                        barcodes.firstOrNull { barcode ->
                                            barcode.rawValue != null &&
                                            barcode.rawValue!!.isNotBlank()
                                        }?.rawValue?.let { value ->
                                            // Debounce: ignore same code within 2 seconds
                                            if (value != lastScannedValue || now - lastScannedTime > 2000) {
                                                lastScannedValue = value
                                                lastScannedTime = now
                                                onQrScanned(value)
                                            }
                                        }
                                    }
                                    .addOnCompleteListener { imageProxy.close() }
                            } else {
                                imageProxy.close()
                            }
                        }
                    }

                try {
                    cameraProvider.unbindAll()
                    val camera = cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageAnalysis
                    )

                    // Enable continuous auto-focus
                    camera.cameraControl.cancelFocusAndMetering()

                    // Report camera controls back
                    onCameraBound(camera.cameraControl, camera.cameraInfo)

                    // Set torch state
                    camera.cameraControl.enableTorch(isTorchOn)

                    // Tap-to-focus via touch listener
                    previewView.setOnTouchListener { view, event ->
                        if (event.action == MotionEvent.ACTION_DOWN) {
                            val factory = previewView.meteringPointFactory
                            val point = factory.createPoint(event.x, event.y)
                            val action = FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
                                .setAutoCancelDuration(3, java.util.concurrent.TimeUnit.SECONDS)
                                .build()
                            camera.cameraControl.startFocusAndMetering(action)
                        }
                        view.performClick()
                        true
                    }

                } catch (e: Exception) { e.printStackTrace() }
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun AnimatedScannerOverlay() {
    val infiniteTransition = rememberInfiniteTransition(label = "scanner")

    // Scanning line animation
    val scanLineY by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(2200, easing = LinearEasing), RepeatMode.Reverse
        ), label = "scanLine"
    )

    // Corner pulse
    val cornerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(1200), RepeatMode.Reverse
        ), label = "cornerAlpha"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Semi-transparent dark overlay with cutout effect
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height
            val frameSize = canvasW * 0.65f
            val left = (canvasW - frameSize) / 2f
            val top = (canvasH - frameSize) / 2f - 40f
            val right = left + frameSize
            val bottom = top + frameSize
            val cornerLen = 40f
            val strokeW = 4f
            val gold = Color(0xFFD4A017).copy(alpha = cornerAlpha)

            // Top-left corner
            drawLine(gold, Offset(left, top), Offset(left + cornerLen, top), strokeW, StrokeCap.Round)
            drawLine(gold, Offset(left, top), Offset(left, top + cornerLen), strokeW, StrokeCap.Round)

            // Top-right corner
            drawLine(gold, Offset(right - cornerLen, top), Offset(right, top), strokeW, StrokeCap.Round)
            drawLine(gold, Offset(right, top), Offset(right, top + cornerLen), strokeW, StrokeCap.Round)

            // Bottom-left corner
            drawLine(gold, Offset(left, bottom - cornerLen), Offset(left, bottom), strokeW, StrokeCap.Round)
            drawLine(gold, Offset(left, bottom), Offset(left + cornerLen, bottom), strokeW, StrokeCap.Round)

            // Bottom-right corner
            drawLine(gold, Offset(right, bottom - cornerLen), Offset(right, bottom), strokeW, StrokeCap.Round)
            drawLine(gold, Offset(right - cornerLen, bottom), Offset(right, bottom), strokeW, StrokeCap.Round)

            // Scanning line
            val lineY = top + (bottom - top) * scanLineY
            drawLine(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color.Transparent,
                        Color(0xFFD4A017).copy(alpha = 0.7f),
                        Color(0xFFD4A017).copy(alpha = 0.9f),
                        Color(0xFFD4A017).copy(alpha = 0.7f),
                        Color.Transparent
                    ),
                    startX = left + 8f,
                    endX = right - 8f
                ),
                start = Offset(left + 8f, lineY),
                end = Offset(right - 8f, lineY),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )
        }
    }
}
