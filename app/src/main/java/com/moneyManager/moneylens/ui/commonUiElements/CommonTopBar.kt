package com.moneyManager.moneylens.ui.commonUiElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneyManager.moneylens.R

@Composable
fun CommonTopBar(
    heading: String,
    showStartIcon: Boolean = true,
    showEndIcon: Boolean = false,
    onStartClick: () -> Unit = {},
    onEndClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (showStartIcon) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onStartClick() }
                    .padding(12.dp),
                painter = painterResource(R.drawable.icon_back_btn),
                contentDescription = "Back",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }

        Text(
            text = heading,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        if (showEndIcon) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onEndClick() }
                    .padding(12.dp),
                painter = painterResource(R.drawable.icon_back_btn),
                contentDescription = "End",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommonTopBarPrev(){
    CommonTopBar("Strategy", true, false)
}
