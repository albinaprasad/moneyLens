package com.moneyManager.moneylens.ui.commonUiElements

import android.R.attr.text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.moneyManager.moneylens.ui.theme.primary_09
import com.moneyManager.moneylens.ui.utils.isLandscape
import kotlinx.coroutines.launch

@Composable
fun CommonButtonBlue(text:String,onClick:()->Unit){
    Button(
    onClick = {
        onClick()
    },
    shape = RoundedCornerShape(16.dp),
    colors = ButtonDefaults.buttonColors(containerColor = primary_09),
    modifier = Modifier
        .padding(
            horizontal = 36.dp,
            vertical = if (isLandscape()) 8.dp else 24.dp
        )
        .fillMaxWidth()
        .height(51.dp)
        .dropShadow(
            shape = RoundedCornerShape(16.dp),
            shadow = Shadow(
                radius = 16.dp,
                spread = 0.dp,
                color = Color.Black.copy(alpha = 0.35f),
                offset = DpOffset(0.dp, 6.dp)
            )
        )
    ) {
        Text(text)
    }
}