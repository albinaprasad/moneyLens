package com.moneyManager.moneylens.ui.Settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.moneyManager.moneylens.DataClass.SettingsCardOptionItem
import com.moneyManager.moneylens.navigation.AppScreens

@Composable
fun SettingsCardComponent(
    modifier: Modifier = Modifier,
    menuItems: List<SettingsCardOptionItem>,
    onOptionClick: (AppScreens) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // "More options" Header Text
        Text(
            text = "More options",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Dark Rounded Container Card
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF1B2026), // Dark slate surface matching the preview image
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                menuItems.forEachIndexed { index, item ->
                    MoreOptionRowItem(
                        icon = item.icon,
                        title = item.title,
                        onClick = {onOptionClick(item.route)}
                    )

                    // Inset divider drawn between list items except after the last item
                    if (index < menuItems.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 56.dp, end = 16.dp),
                            thickness = 0.5.dp,
                            color = Color.White.copy(alpha = 0.08f)
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun MoreOptionRowItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Leading Option Icon
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.85f),
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Title Label
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )

        // Trailing Chevron Arrow
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.4f),
            modifier = Modifier.size(18.dp)
        )
    }
}