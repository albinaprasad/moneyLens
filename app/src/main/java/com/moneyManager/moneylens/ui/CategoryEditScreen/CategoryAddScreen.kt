package com.moneyManager.moneylens.ui.CategoryEditScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneyManager.moneylens.ui.commonUiElements.CommonTabSelector
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar
import kotlin.math.roundToInt

private val PresetColors = listOf(
    Color(0xFFE77E64), // Warm Coral (Default)
    Color(0xFFE6778B), // Soft Rose
    Color(0xFFF48FB1), // Pink
    Color(0xFF7994E8), // Periwinkle Blue
    Color(0xFF69B5F8), // Sky Blue
    Color(0xFF6ECAB2), // Mint Teal
    Color(0xFF97CD78)  // Lime Green
)

private val RainbowSweepColors = listOf(
    Color(0xFFFF4B3E),
    Color(0xFFFF9838),
    Color(0xFFFFE047),
    Color(0xFF4CE073),
    Color(0xFF3AD8E8),
    Color(0xFF567DF4),
    Color(0xFFA855F7),
    Color(0xFFFF4B3E)
)

data class CategoryIcon(
    val name: String,
    val icon: ImageVector
)

object CategoryIcons {
    val all = listOf(
        CategoryIcon("Food", Icons.Filled.Restaurant),
        CategoryIcon("Shopping", Icons.Filled.ShoppingBag),
        CategoryIcon("Transport", Icons.Filled.DirectionsCar),
        CategoryIcon("Home", Icons.Filled.Home),
        CategoryIcon("Medical", Icons.Filled.LocalHospital),
        CategoryIcon("Health", Icons.Filled.MedicalServices),

        CategoryIcon("Education", Icons.Filled.School),
        CategoryIcon("Movies", Icons.Filled.Movie),
        CategoryIcon("Entertainment", Icons.Filled.PlayCircle),
        CategoryIcon("Travel", Icons.Filled.Flight),
        CategoryIcon("Pets", Icons.Filled.Pets),
        CategoryIcon("Bills", Icons.Filled.Receipt),

        CategoryIcon("Savings", Icons.Filled.Savings),
        CategoryIcon("Gifts", Icons.Filled.CardGiftcard),
        CategoryIcon("Fitness", Icons.Filled.FitnessCenter),
        CategoryIcon("Phone", Icons.Filled.Smartphone),
        CategoryIcon("Camera", Icons.Filled.CameraAlt),
        CategoryIcon("Work", Icons.Filled.Work),

        CategoryIcon("Fuel", Icons.Filled.LocalGasStation),
        CategoryIcon("Wallet", Icons.Filled.AccountBalanceWallet),
        CategoryIcon("Coffee", Icons.Filled.LocalCafe),
        CategoryIcon("Music", Icons.Filled.MusicNote),
        CategoryIcon("Sports", Icons.Filled.SportsSoccer),
        CategoryIcon("Beauty", Icons.Filled.Face),
        CategoryIcon("Clothing", Icons.Filled.Checkroom),
        CategoryIcon("Internet", Icons.Filled.Wifi),
        CategoryIcon("Water", Icons.Filled.WaterDrop),
        CategoryIcon("Electricity", Icons.Filled.ElectricBolt),
        CategoryIcon("Bank", Icons.Filled.AccountBalance),
        CategoryIcon("Other", Icons.Filled.Category)
    )
}

@Composable
fun CategoryAddScreen(
    onBackClick: () -> Unit = {},
    onCreateCategory: (name: String, type: String, iconName: String, color: Color) -> Unit = { _, _, _, _ -> }
) {
    var selectedType by remember { mutableStateOf("EXPENSE") }
    var categoryName by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("Shopping") }

    // 0 = Rainbow Custom Hue mode, 1..7 = Solid Preset Color index
    var selectedSwatchIndex by remember { mutableIntStateOf(1) }
    var customHueFraction by remember { mutableFloatStateOf(0.55f) }
    var opacityFraction by remember { mutableFloatStateOf(1.0f) }

    val isRainbowMode = selectedSwatchIndex == 0

    val activeBaseColor = if (isRainbowMode) {
        Color.hsv(hue = customHueFraction * 360f, saturation = 0.58f, value = 0.96f)
    } else {
        PresetColors[(selectedSwatchIndex - 1).coerceIn(PresetColors.indices)]
            .copy(alpha = opacityFraction)
    }

    val animatedAccent by animateColorAsState(
        targetValue = activeBaseColor,
        animationSpec = tween(durationMillis = 250),
        label = "accentColor"
    )

    val selectedImage = CategoryIcons.all
        .firstOrNull { it.name == selectedIcon }
        ?.icon ?: Icons.Filled.ShoppingBag

    val displayCategoryName = categoryName.trim().ifEmpty { "Shopping" }
    val selectedTypeIndex = if (selectedType == "EXPENSE") 0 else 1

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CommonTopBar(
                heading = "New category",
                onStartClick = onBackClick
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Button(
                    onClick = {
                        onCreateCategory(
                            displayCategoryName,
                            selectedType,
                            selectedIcon,
                            activeBaseColor
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                ) {
                    Text(
                        text = "Create Category",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { innerPadding ->
        // Single unified LazyColumn: zero nested scroll traps
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 8.dp,
                bottom = 24.dp
            )
        ) {
            // 1. Tab Selector
            item(key = "type_selector") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 22.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.widthIn(max = 280.dp)) {
                        CommonTabSelector(
                            tabs = listOf("Spending", "Income"),
                            selectedTab = selectedTypeIndex,
                            onTabSelected = { index ->
                                selectedType = if (index == 0) "EXPENSE" else "INCOME"
                            }
                        )
                    }
                }
            }

            // 2. Category Name Input
            item(key = "name_input") {
                Column(
                    modifier = Modifier.padding(bottom = 22.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Category name",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 12.dp)
                    )

                    BasicTextField(
                        value = categoryName,
                        onValueChange = { categoryName = it },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        cursorBrush = SolidColor(animatedAccent.copy(alpha = 1f)),
                        decorationBox = { innerTextField ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp)
                                    .background(MaterialTheme.colorScheme.surfaceContainerHigh, CircleShape)
                                    .padding(horizontal = 20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.weight(1f)) {
                                    if (categoryName.isEmpty()) {
                                        Text(
                                            text = "e.g. Shopping",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    innerTextField()
                                }
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Edit name",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    )
                }
            }

            // 3. Tactile Live Preview Card
            item(key = "live_preview") {
                Box(modifier = Modifier.padding(bottom = 22.dp)) {
                    ExpressiveLivePreviewCard(
                        categoryName = displayCategoryName,
                        categoryType = if (selectedType == "EXPENSE") "Spending" else "Income",
                        iconName = selectedIcon,
                        icon = selectedImage,
                        accentColor = animatedAccent
                    )
                }
            }

            // 4. Colour Picker
            item(key = "colour_picker") {
                Column(
                    modifier = Modifier.padding(bottom = 22.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Choose a colour",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = if (isRainbowMode) "Custom Hue" else "${(opacityFraction * 100).roundToInt()}% Opacity",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val isRainbowSelected = selectedSwatchIndex == 0
                        val rainbowScale by animateFloatAsState(
                            targetValue = if (isRainbowSelected) 1.15f else 1f,
                            animationSpec = tween(durationMillis = 200),
                            label = "rainbowSwatchScale"
                        )

                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .scale(rainbowScale)
                                .clip(CircleShape)
                                .then(
                                    if (isRainbowSelected) {
                                        Modifier
                                            .border(2.dp, animatedAccent.copy(alpha = 1f), CircleShape)
                                            .padding(4.dp)
                                    } else {
                                        Modifier
                                    }
                                )
                                .background(Brush.sweepGradient(RainbowSweepColors), CircleShape)
                                .clickable { selectedSwatchIndex = 0 },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Tune,
                                contentDescription = "Custom rainbow colour",
                                tint = Color(0xFF101114),
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        PresetColors.forEachIndexed { index, swatchColor ->
                            val swatchIndex = index + 1
                            val isSelected = selectedSwatchIndex == swatchIndex
                            val scale by animateFloatAsState(
                                targetValue = if (isSelected) 1.15f else 1f,
                                animationSpec = tween(durationMillis = 200),
                                label = "swatchScale"
                            )

                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .scale(scale)
                                    .clip(CircleShape)
                                    .then(
                                        if (isSelected) {
                                            Modifier
                                                .border(2.dp, swatchColor, CircleShape)
                                                .padding(4.dp)
                                        } else {
                                            Modifier
                                        }
                                    )
                                    .background(swatchColor, CircleShape)
                                    .clickable {
                                        selectedSwatchIndex = swatchIndex
                                        opacityFraction = 1.0f
                                    }
                            )
                        }
                    }

                    ExpressivePixelColorSlider(
                        isRainbowMode = isRainbowMode,
                        sliderPosition = if (isRainbowMode) {
                            customHueFraction
                        } else {
                            ((opacityFraction - 0.20f) / 0.80f).coerceIn(0f, 1f)
                        },
                        baseSolidColor = if (isRainbowMode) {
                            animatedAccent.copy(alpha = 1f)
                        } else {
                            PresetColors[(selectedSwatchIndex - 1).coerceIn(PresetColors.indices)]
                        },
                        thumbColor = animatedAccent.copy(alpha = 1f),
                        onPositionChanged = { pos ->
                            if (isRainbowMode) {
                                customHueFraction = pos
                            } else {
                                opacityFraction = 0.20f + (pos * 0.80f)
                            }
                        }
                    )
                }
            }

            // 5. Icon Picker Header
            item(key = "icon_header") {
                Text(
                    text = "Choose an icon",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // 6. Custom Lazy Grid Adapter inside the same LazyColumn!
            gridItems(
                items = CategoryIcons.all,
                columns = 6,
                key = { it.name },
                horizontalSpacing = 10.dp,
                verticalSpacing = 10.dp
            ) { categoryIcon ->
                ExpressiveIconTile(
                    categoryIcon = categoryIcon,
                    isSelected = selectedIcon == categoryIcon.name,
                    accentColor = animatedAccent,
                    onClick = { selectedIcon = categoryIcon.name }
                )
            }
        }
    }
}

/**
 * Reusable Grid Adapter for LazyColumn.
 */
fun <T> LazyListScope.gridItems(
    items: List<T>,
    columns: Int,
    key: ((T) -> Any)? = null,
    horizontalSpacing: Dp = 10.dp,
    verticalSpacing: Dp = 10.dp,
    itemContent: @Composable (T) -> Unit
) {
    val rows = items.chunked(columns)

    items(
        count = rows.size,
        key = { rowIndex ->
            if (key != null) {
                "grid_row_${key(rows[rowIndex].first())}"
            } else {
                "grid_row_$rowIndex"
            }
        }
    ) { rowIndex ->
        val rowItems = rows[rowIndex]

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = if (rowIndex == 0) 0.dp else verticalSpacing / 2,
                    bottom = if (rowIndex == rows.lastIndex) 0.dp else verticalSpacing / 2
                ),
            horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
        ) {
            rowItems.forEach { item ->
                Box(modifier = Modifier.weight(1f)) {
                    itemContent(item)
                }
            }

            val emptySlots = columns - rowItems.size
            if (emptySlots > 0) {
                repeat(emptySlots) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ExpressiveIconTile(
    categoryIcon: CategoryIcon,
    isSelected: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val solidAccent = accentColor.copy(alpha = 1f)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cornerRadius by animateDpAsState(
        targetValue = when {
            isPressed -> 10.dp
            isSelected -> 28.dp
            else -> 14.dp
        },
        animationSpec = tween(durationMillis = 200),
        label = "iconCornerMorph"
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.90f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "iconPressScale"
    )

    val tileShape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .scale(scale)
            .clip(tileShape)
            .background(
                color = if (isSelected)
                    solidAccent.copy(alpha = (0.28f * accentColor.alpha).coerceAtLeast(0.10f))
                else
                    MaterialTheme.colorScheme.surfaceContainerHigh
            )
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 1.5.dp,
                        color = accentColor.copy(alpha = accentColor.alpha.coerceAtLeast(0.45f)),
                        shape = tileShape
                    )
                } else {
                    Modifier
                }
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = categoryIcon.icon,
            contentDescription = categoryIcon.name,
            tint = if (isSelected) solidAccent else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ExpressiveLivePreviewCard(
    categoryName: String,
    categoryType: String,
    iconName: String,
    icon: ImageVector,
    accentColor: Color
) {
    val badgeRotation by animateFloatAsState(
        targetValue = (iconName.hashCode() % 3) * 12f,
        animationSpec = tween(durationMillis = 300),
        label = "badgeRotation"
    )

    val cookieShape = MaterialShapes.Cookie9Sided.toShape()

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 2.dp,
        shadowElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .rotate(badgeRotation)
                    .clip(cookieShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                accentColor.copy(alpha = (accentColor.alpha * 0.85f).coerceIn(0f, 1f)),
                                accentColor
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (accentColor.alpha > 0.55f) Color(0xFF191210) else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .rotate(-badgeRotation)
                        .size(27.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = categoryType,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = accentColor.copy(alpha = accentColor.alpha.coerceAtLeast(0.55f))
                )
            }
        }
    }
}

@Composable
private fun ExpressivePixelColorSlider(
    isRainbowMode: Boolean,
    sliderPosition: Float,
    baseSolidColor: Color,
    thumbColor: Color,
    onPositionChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val hueColors = remember {
        listOf(
            Color(0xFFFF4B3E), // Red
            Color(0xFFFF9838), // Orange
            Color(0xFFFFE047), // Yellow
            Color(0xFF4CE073), // Green
            Color(0xFF3AD8E8), // Cyan
            Color(0xFF567DF4), // Blue
            Color(0xFFA855F7), // Purple
            Color(0xFFEC4899), // Pink
            Color(0xFFF43F5E)  // Magenta
        )
    }

    var barWidthPx by remember { mutableFloatStateOf(1f) }
    var isDragging by remember { mutableStateOf(false) }

    val currentOnPositionChanged by rememberUpdatedState(onPositionChanged)

    val handleWidth by animateDpAsState(
        targetValue = if (isDragging) 6.dp else 4.dp,
        animationSpec = tween(durationMillis = 150),
        label = "pixelHandleWidth"
    )
    val handleHeight by animateDpAsState(
        targetValue = if (isDragging) 40.dp else 36.dp,
        animationSpec = tween(durationMillis = 150),
        label = "pixelHandleHeight"
    )

    val density = LocalDensity.current
    val trackHeight = 28.dp
    val handleGap = 6.dp

    fun updateFromX(x: Float) {
        val clamped = (x / barWidthPx).coerceIn(0f, 1f)
        currentOnPositionChanged(clamped)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp)
            .onSizeChanged { barWidthPx = it.width.toFloat().coerceAtLeast(1f) }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        isDragging = true
                        updateFromX(offset.x)
                        tryAwaitRelease()
                        isDragging = false
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = { isDragging = false },
                    onDragCancel = { isDragging = false }
                ) { change, _ ->
                    change.consume()
                    updateFromX(change.position.x)
                }
            },
        contentAlignment = Alignment.CenterStart
    ) {
        val handleWidthPx = with(density) { handleWidth.toPx() }
        val handleGapPx = with(density) { handleGap.toPx() }
        val usableWidth = (barWidthPx - handleWidthPx).coerceAtLeast(1f)
        val clampedPos = sliderPosition.coerceIn(0f, 1f)

        val handleCenterX = (usableWidth * clampedPos) + (handleWidthPx / 2f)

        val leftTrackWidthPx = (handleCenterX - (handleWidthPx / 2f) - handleGapPx).coerceAtLeast(0f)
        val rightTrackStartPx = (handleCenterX + (handleWidthPx / 2f) + handleGapPx).coerceAtMost(barWidthPx)
        val rightTrackWidthPx = (barWidthPx - rightTrackStartPx).coerceAtLeast(0f)

        val trackBrush = remember(barWidthPx, isRainbowMode, baseSolidColor) {
            if (isRainbowMode) {
                Brush.horizontalGradient(
                    colors = hueColors,
                    startX = 0f,
                    endX = barWidthPx
                )
            } else {
                Brush.horizontalGradient(
                    colors = listOf(
                        baseSolidColor.copy(alpha = 0.20f),
                        baseSolidColor.copy(alpha = 1.0f)
                    ),
                    startX = 0f,
                    endX = barWidthPx
                )
            }
        }

        if (leftTrackWidthPx > 0f) {
            Box(
                modifier = Modifier
                    .width(with(density) { leftTrackWidthPx.toDp() })
                    .height(trackHeight)
                    .clip(
                        RoundedCornerShape(
                            topStart = 14.dp,
                            bottomStart = 14.dp,
                            topEnd = 4.dp,
                            bottomEnd = 4.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .background(trackBrush)
            )
        }

        if (rightTrackWidthPx > 0f) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(rightTrackStartPx.roundToInt(), 0) }
                    .width(with(density) { rightTrackWidthPx.toDp() })
                    .height(trackHeight)
                    .clip(
                        RoundedCornerShape(
                            topStart = 4.dp,
                            bottomStart = 4.dp,
                            topEnd = 14.dp,
                            bottomEnd = 14.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .background(trackBrush, alpha = 0.28f),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (rightTrackWidthPx > with(density) { 16.dp.toPx() }) {
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(4.dp)
                            .background(
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                CircleShape
                            )
                    )
                }
            }
        }

        val thumbOffsetX = (handleCenterX - (handleWidthPx / 2f)).roundToInt()
        Box(
            modifier = Modifier
                .offset { IntOffset(thumbOffsetX, 0) }
                .width(handleWidth)
                .height(handleHeight)
                .clip(CircleShape)
                .background(thumbColor)
        )
    }
}