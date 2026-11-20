package com.moneyManager.moneylens.ui.Settings

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color Palette matched to the UI reference[cite: 1]
val DarkHeaderBg = Color(0xFF1C1C1E)
val DarkCardBg = Color(0xFF262629)
val DarkIconButtonBg = Color(0xFF2C2C2E)
val LightContentBg = Color(0xFFFAFAFA)
val CardBorderColor = Color(0xFFEEEEEE)
val TextPrimaryDark = Color(0xFF1E293B)
val TextSecondaryLight = Color(0xFFA1A1AA)
val IconTintDark = Color(0xFF334155)
val SwitchActiveColor = Color(0xFF10B981)

data class QuickSettingToggle(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val initialState: Boolean
)

data class SettingsMenuItem(
    val title: String,
    val icon: ImageVector,
    val subtitle: String? = null,
    val onClick: () -> Unit = {}
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    // Dummy states for top toggle cards[cite: 1]
    val quickToggles = remember {
        mutableStateListOf(
            QuickSettingToggle("notif", "Notifications", Icons.Outlined.Notifications, true),
            QuickSettingToggle("dark", "Dark Mode", Icons.Outlined.DarkMode, false),
            QuickSettingToggle("bio", "Biometric", Icons.Outlined.Fingerprint, true)
        )
    }

    // Requested bottom selection items[cite: 1]
    val bottomMenuItems = remember {
        listOf(
            SettingsMenuItem("Invite Friends", Icons.Outlined.PersonAdd, "Earn $10 per referral"),
            SettingsMenuItem("Rate Us", Icons.Outlined.StarOutline, "Love the app? Let us know"),
            SettingsMenuItem("Feedback", Icons.Outlined.RateReview, "Share your thoughts"),
            SettingsMenuItem("FAQ", Icons.AutoMirrored.Outlined.HelpOutline, "Common questions answered"),
            SettingsMenuItem("About", Icons.Outlined.Info, "Version 2.4.1 (Build 108)")
        )
    }

    // Inbuilt Material 3 Expressive shapes used by LoadingIndicator to morph the outer container
    val expressiveShapes = remember {
        listOf(
            MaterialShapes.Pentagon,
            MaterialShapes.Clover4Leaf,
            MaterialShapes.Cookie9Sided,
            MaterialShapes.SoftBurst,
            MaterialShapes.Sunny,
            MaterialShapes.Cookie4Sided,
            MaterialShapes.Clover8Leaf
        )
    }

    // Custom slow progress driver (3 seconds per shape morph = 15s full cycle)
    val infiniteTransition = rememberInfiniteTransition(label = "SlowExpressiveMorph")
    val slowProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000 * expressiveShapes.size,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "MorphProgress"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightContentBg)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(DarkHeaderBg)
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Top Bar (Back Button, Title, Trailing Icon)[cite: 1]
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularHeaderButton(
                        icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        onClick = onBackClick
                    )

                    Text(
                        text = "Settings",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    CircularHeaderButton(
                        icon = Icons.Outlined.Tune,
                        contentDescription = "Preferences",
                        onClick = onActionClick
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Slow-morphing Material 3 Expressive LoadingIndicator behind a static inner icon
                Box(
                    modifier = Modifier.size(92.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator(
                        progress = { slowProgress },
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xFF0057E7),
                        polygons = expressiveShapes
                    )

                    // Inner icon/image stays static while the outer expressive shape morphs
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Profile Avatar",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "App Preferences",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Janeper01@gmail.com • Pro Plan",
                    color = TextSecondaryLight,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

            }
        }

        // --- BOTTOM LIGHT SELECTION LIST ---
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(bottomMenuItems) { menuItem ->
                SettingsSelectionRow(item = menuItem)
            }
        }
    }
}

@Composable
private fun CircularHeaderButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(DarkIconButtonBg)
            .border(1.dp, Color.White.copy(alpha = 0.08f), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun QuickToggleCard(
    item: QuickSettingToggle,
    modifier: Modifier = Modifier,
    onToggle: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(92.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(16.dp),
        color = DarkCardBg,
        border = BorderStroke(
            width = 1.dp,
            color = if (item.initialState) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.06f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = if (item.initialState) Color.White else TextSecondaryLight,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = if (item.initialState) "ON" else "OFF",
                color = if (item.initialState) SwitchActiveColor else TextSecondaryLight,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun SettingsSelectionRow(item: SettingsMenuItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { item.onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, CardBorderColor),
        shadowElevation = 0.5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = IconTintDark,
                modifier = Modifier.size(22.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    color = TextPrimaryDark,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
                if (item.subtitle != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = item.subtitle,
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Navigate",
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen()
    }
}