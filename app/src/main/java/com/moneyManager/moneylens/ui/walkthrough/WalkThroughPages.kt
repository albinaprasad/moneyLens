package com.moneyManager.moneylens.ui.walkthrough

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneyManager.moneylens.R
import com.moneyManager.moneylens.ui.theme.primary_09
import com.moneyManager.moneylens.ui.theme.secondary
import com.moneyManager.moneylens.ui.theme.white
import com.moneyManager.moneylens.ui.walkthrough.data.WalkThroughPageData

@Composable
fun walkThroughPages(page: WalkThroughPageData) {
    Column(
        modifier = Modifier
            .background(white)
            .wrapContentHeight()
            .padding(26.dp)
            ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp))
        Image(
            painter = painterResource(R.drawable.walthrough),
            contentDescription = "",
            modifier = Modifier.size(254.dp, 254.dp)
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
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(8.dp))

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
    }
}
