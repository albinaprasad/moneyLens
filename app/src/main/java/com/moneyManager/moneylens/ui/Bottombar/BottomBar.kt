package com.moneyManager.moneylens.ui.Bottombar

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moneyManager.moneylens.DataClass.BottomNavigationItems

@Composable
fun BottomBar(
    bottomNavController: NavHostController,
    bottomNavItems: List<BottomNavigationItems>
) {
    // Current selection state
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        tonalElevation = 12.dp,
        modifier = Modifier.fillMaxWidth()) {
        bottomNavItems.forEachIndexed { index, item ->
            val isSelected = selectedItem == index
            
            NavigationBarItem(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                selected = isSelected,
                onClick = { 
                    selectedItem = index
                    // Navigation logic:
                    // bottomNavController.navigate(item.name) 
                },
                interactionSource = remember { MutableInteractionSource() },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.name,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary 
                               else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                label = {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
