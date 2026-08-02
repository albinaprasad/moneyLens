package com.moneyManager.moneylens.ui.TopScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.moneyManager.moneylens.R

@Composable
fun BudgetProgressCard(
    modifier: Modifier = Modifier,
    progress: Float = 0.56f,
    spent: String = "₹1013",
    limit: String = "₹1800",
    remaining: String = "₹787"
) {

    var selectedTab by remember { mutableStateOf(0) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        val (
            toggle,
            progressBox,
            spentRef,
            limitRef
        ) = createRefs()

        // -----------------------------
        // Monthly / Annual
        // -----------------------------

        Row(
            modifier = Modifier.constrainAs(toggle) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            horizontalArrangement = Arrangement.Center
        ) {

            TextButton(
                onClick = { selectedTab = 0 }
            ) {
                Text(
                    "Monthly",
                    color = if (selectedTab == 0) Color.White else Color.Gray
                )
            }

            Spacer(Modifier.width(12.dp))

            TextButton(
                onClick = { selectedTab = 1 }
            ) {
                Text(
                    "Annual",
                    color = if (selectedTab == 1) Color.White else Color.Gray
                )
            }
        }

        // -----------------------------
        // Progress + Remaining
        // -----------------------------

        Box(
            modifier = Modifier
                .fillMaxWidth(.85f)
                .aspectRatio(2f)
                .constrainAs(progressBox) {
                    top.linkTo(toggle.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentAlignment = Alignment.BottomCenter
        ) {

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {

                val strokeWidth = size.width * 0.08f

                val stroke = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )

                val padding = strokeWidth / 2
                val arcDiameter = size.width - strokeWidth

                drawArc(
                    color = Color.DarkGray,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(padding, padding),
                    size = Size(arcDiameter, arcDiameter),
                    style = stroke
                )

                drawArc(
                    color = Color(0xFFFFC72C),
                    startAngle = 180f,
                    sweepAngle = progress * 180f,
                    useCenter = false,
                    topLeft = Offset(padding, padding),
                    size = Size(arcDiameter, arcDiameter),
                    style = stroke
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {

                Text(
                    "REMAINING",
                    color = Color.Gray,
                    fontSize = 13.sp
                )

                Text(
                    "ancacnx xcxcxcxcxcxcx",
                    color = Color.Green,
                    fontSize = 34.sp,
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = 16.sp,
                        maxFontSize = 24.sp,
                        stepSize = 2.sp
                    ),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // -----------------------------
        // Spent
        // -----------------------------

        Column(
            modifier = Modifier.constrainAs(spentRef) {
                top.linkTo(progressBox.bottom, margin = 12.dp)
                start.linkTo(progressBox.start)
            }
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    tint = Color(0xFFFFC72C),
                    modifier = Modifier.size(18.dp)
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    spent,
                    color = Color.White
                )
            }

            Text(
                "Spent",
                color = Color.Gray
            )
        }

        // -----------------------------
        // Limit
        // -----------------------------

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.constrainAs(limitRef) {
                top.linkTo(progressBox.bottom, margin = 12.dp)
                end.linkTo(progressBox.end)
            }
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    limit,
                    color = Color.White
                )

                Spacer(Modifier.width(4.dp))

                Icon(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    tint = Color(0xFFFFC72C),
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                "Limit",
                color = Color.Gray
            )
        }
    }
}
