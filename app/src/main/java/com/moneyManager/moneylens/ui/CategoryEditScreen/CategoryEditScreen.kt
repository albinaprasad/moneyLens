package com.moneyManager.moneylens.ui.CategoryEditScreen

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moneyManager.moneylens.AnimationManager.AnimationManager.jiggle
import com.moneyManager.moneylens.database.Entity.Category
import com.moneyManager.moneylens.ui.commonUiElements.CommonTabSelector
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyGridState


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

    val defaultCategory = if (selectedType == 0) {
        "Shopping"
    } else {
        "Salary"
    }
    val source = if (selectedType == 0) expenseCategories else incomeCategories
    var items by remember(source) { mutableStateOf(source) }
    val currentItems by rememberUpdatedState(items)
    val gridState = rememberLazyGridState()
    val reorderState = rememberReorderableLazyGridState(gridState) { from, to ->
        val fromIndex = items.indexOfFirst { it.id == from.key }
        val toIndex = items.indexOfFirst { it.id == to.key }
        if (fromIndex != -1 && toIndex != -1) {
            items = items.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
        }
    }
    val haptics = LocalHapticFeedback.current
    var isEditMode by remember { mutableStateOf(false) }

    var dragCenter by remember { mutableStateOf<Offset?>(null) }
    var deleteBounds by remember { mutableStateOf<Rect?>(null) }
    var showDeleteTarget by remember { mutableStateOf(false) }

    val isOverDelete by remember {
        derivedStateOf {
            val center = dragCenter
            val bounds = deleteBounds
            val margin = 40f

            showDeleteTarget && center != null && bounds != null &&
                    center.x in (bounds.left - margin)..(bounds.right + margin) &&
                    center.y in (bounds.top - margin)..(bounds.bottom + margin)
        }
    }

    LaunchedEffect(isOverDelete) {
        if (isOverDelete) haptics.performHapticFeedback(HapticFeedbackType.LongPress)
    }
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

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = gridState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(
                    start = 16.dp, end = 16.dp, top = 24.dp, bottom = 100.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item(key = "tabs", span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(modifier = Modifier.widthIn(max = 280.dp)) {
                            CommonTabSelector(
                                tabs = listOf("Spending", "Income"),
                                selectedTab = selectedType,
                                onTabSelected = { selectedType = it }
                            )
                        }
                    }
                }

                item(key = "default", span = { GridItemSpan(maxLineSpan) }) {
                    DefaultCategoryCard(
                        categoryName = defaultCategory,
                        onClick = onDefaultCategoryClick
                    )
                }

                item(key = "editOrder", span = { GridItemSpan(maxLineSpan) }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        EditOrderButton(onClick = onEditOrderClick)
                    }
                }
                items(items, key = { it.id }) { category ->
                    ReorderableItem(reorderState, key = category.id) { isDragging ->
                        val elevation by animateDpAsState(
                            if (isDragging) 12.dp else 3.dp,
                            label = "dragElevation"
                        )
                        val scale by animateFloatAsState(
                            targetValue = when {
                                isDragging && isOverDelete -> 0.5f
                                isDragging -> 1.06f
                                else -> 1f
                            },
                            label = "dragScale"
                        )

                        CategoryCard(
                            category = category,
                            viewModel = viewModal,
                            elevation = elevation,
                            onClick = { onCategoryClick(category.name) },
                            modifier = Modifier
                                .onGloballyPositioned { coords ->
                                    if (isDragging) dragCenter = coords.boundsInRoot().center
                                }
                                .jiggle(enabled = isEditMode && !isDragging, seed = category.id)
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                }
                                .longPressDraggableHandle(
                                    onDragStarted = {
                                        isEditMode = true
                                        showDeleteTarget = true
                                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                    },
                                    onDragStopped = {
                                        val shouldDelete = isOverDelete

                                        isEditMode = false
                                        showDeleteTarget = false
                                        dragCenter = null
                                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)

                                        val remaining = if (shouldDelete) {
                                            currentItems.filter { it.id != category.id }
                                        } else {
                                            currentItems
                                        }
                                        items = remaining
                                        if (shouldDelete) viewModal.deleteCategory(category)
                                        viewModal.saveOrder(remaining)
                                    }
                                )
                        )
                    }
                }
                item(key = "Spacer", span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(Modifier.fillMaxWidth().height(55.dp))
                }
            }

        }


        val fabScale by animateFloatAsState(
            targetValue = when {
                isOverDelete -> 1.35f
                showDeleteTarget -> 1.1f
                else -> 1f
            },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            label = "fabScale"
        )

        val fabBias by animateFloatAsState(
            targetValue = if (showDeleteTarget) 0f else 1f,
            animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
            label = "fabBias"
        )

        val fabColor by animateColorAsState(
            targetValue = if (isOverDelete) {
                MaterialTheme.colorScheme.error
            } else if (showDeleteTarget) {
                MaterialTheme.colorScheme.errorContainer
            } else {
                MaterialTheme.colorScheme.primaryContainer
            },
            animationSpec = tween(durationMillis = 250),
            label = "fabColor"
        )

        val fabElevation by animateDpAsState(
            targetValue = if (isOverDelete) 12.dp else 6.dp,
            label = "fabElevation"
        )

        FloatingActionButton(
            onClick = { if (!showDeleteTarget) onAddCategoryClick() },
            modifier = Modifier
                .align(BiasAlignment(fabBias, 1f))
                .navigationBarsPadding()
                .padding(
                    end = 22.dp * fabBias,
                    bottom = 22.dp
                )
                .onGloballyPositioned { deleteBounds = it.boundsInRoot() }
                .graphicsLayer {
                    scaleX = fabScale
                    scaleY = fabScale
                },
            shape = RoundedCornerShape(if (showDeleteTarget) 20.dp else 16.dp),
            containerColor = fabColor,
            contentColor = if (isOverDelete) {
                MaterialTheme.colorScheme.onError
            } else if (showDeleteTarget) {
                MaterialTheme.colorScheme.onErrorContainer
            } else {
                MaterialTheme.colorScheme.onPrimaryContainer
            },
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = fabElevation)
        ) {

            Crossfade(
                targetState = showDeleteTarget,
                animationSpec = tween(durationMillis = 200),
                label = "fabIcon"
            ) { isDeleting ->
                Icon(
                    imageVector = if (isDeleting) Icons.Default.Delete else Icons.Default.Add,
                    contentDescription = "Add category"
                )
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
    elevation: Dp = 3.dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    viewModel: CategoryEditViewModal
) {
    Surface(
        onClick = onClick,
        modifier = modifier.aspectRatio(1.48f),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = elevation
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
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}