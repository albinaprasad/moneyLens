package com.moneyManager.moneylens.ui.commonUiElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
            .height(40.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (showStartIcon) {
            Image(
                modifier = Modifier.clickable { onStartClick() },
                painter = painterResource(R.drawable.icon_back_btn),
                contentDescription = "Back"
            )
        } else {
            Spacer(modifier = Modifier.width(24.dp))
        }

        Text(
            text = heading,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
        )

        if (showEndIcon) {
            Image(
                modifier = Modifier.clickable { onEndClick() },
                painter = painterResource(R.drawable.icon_back_btn),
                contentDescription = "End"
            )
        } else {
            Spacer(modifier = Modifier.width(24.dp))
        }
    }
}

@Preview
@Composable
fun CommonTopBarPrev(){
CommonTopBar("StraterGY",true,false)
}