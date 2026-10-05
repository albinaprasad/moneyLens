package com.moneyManager.moneylens.ui.SaveScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyManager.moneylens.ui.SaveScreen.CategoryBottomSheet.AccountSheet
import com.moneyManager.moneylens.ui.SaveScreen.CategoryBottomSheet.CategorySheet
import com.moneyManager.moneylens.ui.commonUiElements.CommonTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveScreen(
    navController: NavHostController,
    viewModel: SaveViewmodal = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val bottomSheetUiState by viewModel.bottomSheetUiState.collectAsState()
    Scaffold(
        topBar = {
            CommonTopBar(
                heading = "Add Transaction",
                onStartClick = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveTransaction {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.imePadding(),
                shape = RoundedCornerShape(12.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            //expence  or Income
            ExpenceIncomeBar(
                selectedType = uiState.type,
                onTypeSelected = viewModel::onTypeSelected
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                DateTimeRow(
                    date = uiState.date,
                    time = uiState.time,
                    onDateSelected = viewModel::onDateSelected,
                    onTimeSelected = viewModel::onTimeSelected
                )

                AmountSection(
                    amount = uiState.amount,
                    onAmountChange = viewModel::onAmountChange,
                    viewModel = viewModel

                )

                TransactionOptionItem(
                    label = "Category",
                    value = uiState.category,
                    leadingIcon = Icons.Default.Category,
                    onClick = {
                        viewModel.openCategorySheet()
                    }
                )

                TransactionOptionItem(
                    label = "Payment mode",
                    value = uiState.paymentMode,
                    leadingIcon = Icons.Default.Payments,
                    onClick = {
                        // payment mode selection later
                        viewModel.openAccountSheet()
                    }
                )

                NoteSection(
                    note = uiState.note,
                    onNoteChange = { note ->
                        viewModel.onNoteChange(note)
                    }
                )

            }
        }
    }

    if (bottomSheetUiState.showCategorySheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.closeCategorySheet()
            }
        ) {
            CategorySheet(
                viewmodal = viewModel,
                categories = bottomSheetUiState.categories,
                onEditClick = {
                    // later
                },
                onCloseClick = {
                    viewModel.closeCategorySheet()
                }
            )
        }
    }

    if (bottomSheetUiState.showAccountSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.closeAccountSheet()
            }
        ) {
            AccountSheet(
                viewmodal = viewModel,
                accounts = bottomSheetUiState.accounts,
                onEditClick = {
                    // later
                },
                onCloseClick = {
                    viewModel.closeAccountSheet()
                }
            )
        }
    }
}

@Composable
fun ExpenceIncomeBar(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(
            8.dp,
            Alignment.CenterHorizontally
        )
    ) {
        TransactionType.values().forEach { type ->
            val isSelected = selectedType == type
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                TextButton(
                    onClick = { onTypeSelected(type) },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.heightIn(min = 24.dp)
                ) {
                    Text(
                        type.label,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}