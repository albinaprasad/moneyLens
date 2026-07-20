package com.moneyManager.moneylens.ui.commonUiElements

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyManager.moneylens.R
import com.moneyManager.moneylens.ui.theme.primary_02
import com.moneyManager.moneylens.ui.theme.primary_06
import com.moneyManager.moneylens.ui.theme.white

@Composable
fun CustomDropDown(modifier: Modifier){
    val options = listOf("Balanced - 50/30/20", "Aggressive - 70/20/10", "Conservative - 30/40/30")
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOption by rememberSaveable { mutableStateOf(options[0]) }
    val arrowRotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "ArrowRotation"
    )
    val interactionSource = remember { MutableInteractionSource() }
    var itemWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentSize(Alignment.TopStart)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    itemWidth = with(density) { coordinates.size.width.toDp() }
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    expanded = !expanded
                }
                .border(
                    width = 1.dp,
                    color = primary_02,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedOption,
                color = primary_06
            )

            Image(
                painter = painterResource(R.drawable.icon_down_arrow),
                contentDescription = "Expand dropdown",
                modifier = Modifier.rotate(arrowRotationAngle)
            )

        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(color = white)
                .width(itemWidth)

        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option, color = primary_06) },
                    onClick = {
                        selectedOption = option
                        expanded = false
                    }
                )
            }
        }

    }

}

