package com.moneyManager.moneylens.ui.walkthrough

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneyManager.moneylens.R
import com.moneyManager.moneylens.ui.theme.primary_09
import com.moneyManager.moneylens.ui.theme.secondary
import com.moneyManager.moneylens.ui.theme.white
import com.moneyManager.moneylens.ui.utils.DeviceOrientation
import com.moneyManager.moneylens.ui.utils.LocalDeviceOrientation
import com.moneyManager.moneylens.ui.walkthrough.data.WalkThroughPageData

@Composable
fun walkThroughPages(page: WalkThroughPageData) {
    val orientation = LocalDeviceOrientation.current

    if (orientation == DeviceOrientation.LANDSCAPE) {
        LandscapeWalkThroughPage(page)
    } else {
        PortraitWalkThroughPage(page)
    }
}

@Composable
private fun PortraitWalkThroughPage(page: WalkThroughPageData) {
    Column(
        modifier = Modifier
            .background(white)
            .wrapContentHeight()
            .padding(26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Image(
            painter = painterResource(R.drawable.walthrough),
            contentDescription = "",
            modifier = Modifier.size(254.dp, 254.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = page.title,
            color = primary_09,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = page.description,
            color = secondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun LandscapeWalkThroughPage(page: WalkThroughPageData) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .padding(horizontal = 32.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.walthrough),
            contentDescription = "",
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .weight(0.4f)
        )

        Spacer(modifier = Modifier.width(24.dp))

        Column(
            modifier = Modifier
                .weight(0.6f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = page.title,
                color = primary_09,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = page.description,
                color = secondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        }
    }
}
