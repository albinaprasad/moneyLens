package com.moneyManager.moneylens.ui.CategoryEditScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneyManager.moneylens.database.Entity.Category
import com.moneyManager.moneylens.ui.commonUiElements.CommonTabSelector
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar

private data class CategoryUiItem(
    val name: String,
    val icon: ImageVector,
    val tint: Color,
    val iconBackground: Color
)

@Composable
fun CategoryEditScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onDefaultCategoryClick: () -> Unit = {},
    onEditOrderClick: () -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
    onAddCategoryClick: () -> Unit = {}
) {
    val viewModal = hiltViewModel<CategoryEditViewModal>()
    var selectedType by remember { mutableIntStateOf(0) }

    val expenseCategories by viewModal.expenseCategories
        .collectAsStateWithLifecycle()

    val incomeCategories by viewModal.incomeCategories
        .collectAsStateWithLifecycle()

    val categories = if (selectedType == 0) {
        expenseCategories
    } else {
        incomeCategories
    }

    val defaultCategory = if (selectedType == 0) {
        "Shopping"
    } else {
        "Salary"
    }

    val categoryRows = categories.chunked(2)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CommonTopBar(
                "Categories",
                onStartClick = onBackClick
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(24.dp))

                CommonTabSelector(
                    tabs = listOf("Spending", "Income"),
                    selectedTab = selectedType,
                    onTabSelected = {selectedType = it }
                )

                Spacer(Modifier.height(22.dp))

                DefaultCategoryCard(
                    categoryName = defaultCategory,
                    onClick = onDefaultCategoryClick
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 22.dp,
                            bottom = 12.dp
                        ),
                    horizontalArrangement = Arrangement.End
                ) {
                    EditOrderButton(
                        onClick = onEditOrderClick
                    )
                }

                categoryRows.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowItems.forEach { category ->
                            CategoryCard(
                                category = category,
                                viewModel = viewModal,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    onCategoryClick(category.name)
                                }
                            )
                        }

                        if (rowItems.size == 1) {
                            Spacer(Modifier.weight(1f))
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                }

                // Prevent the floating button from covering the last row.
                Spacer(Modifier.height(76.dp))
            }
        }

        // Floating add button.
        FloatingActionButton(
            onClick = onAddCategoryClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(
                    end = 22.dp,
                    bottom = 22.dp
                ),
            shape = RoundedCornerShape(12.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add category"
            )
        }
    }
}

@Composable
private fun CategoryTypeSelector(
    selectedType: Int,
    onTypeSelected: (Int) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme
            .surfaceVariant
            .copy(alpha = 0.5f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            listOf("Expense", "Income").forEachIndexed { index, label ->
                val selected = index == selectedType

                val selectedBackground by animateColorAsState(
                    targetValue = if (selected) {
                        MaterialTheme.colorScheme.surface
                    } else {
                        Color.Transparent
                    },
                    label = "categoryTabBackground"
                )

                val selectedTextColor by animateColorAsState(
                    targetValue = if (selected) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme
                            .onSurfaceVariant
                            .copy(alpha = 0.65f)
                    },
                    label = "categoryTabText"
                )

                Surface(
                    onClick = {
                        onTypeSelected(index)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50),
                    color = selectedBackground,
                    shadowElevation = if (selected) 2.dp else 0.dp
                ) {
                    Box(
                        modifier = Modifier.padding(
                            vertical = 14.dp,
                            horizontal = 8.dp
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = selectedTextColor,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (selected) {
                                FontWeight.SemiBold
                            } else {
                                FontWeight.Medium
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DefaultCategoryCard(
    categoryName: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 18.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Default Category",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(3.dp))

                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Choose default category",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun EditOrderButton(
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 11.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = "Edit order",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun CategoryCard(
    category: Category,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    viewModel: CategoryEditViewModal
) {
    Surface(
        onClick = onClick,
        modifier = modifier.aspectRatio(1.48f),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 8.dp,
                    vertical = 12.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(category.color).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = viewModel.getCategoryIcon(category.icon),
                    contentDescription = category.name,
                    tint = Color(category.color),
                    modifier = Modifier.size(25.dp)
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = category.name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 15.sp,
                lineHeight = 18.sp
            )
        }
    }
}