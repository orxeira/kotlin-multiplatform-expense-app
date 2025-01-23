package gal.orxeira.expenseapp.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import expenseapp.shared.generated.resources.Res
import expenseapp.shared.generated.resources.amount
import expenseapp.shared.generated.resources.cancel
import expenseapp.shared.generated.resources.category
import expenseapp.shared.generated.resources.confirm_delete_expense
import expenseapp.shared.generated.resources.date
import expenseapp.shared.generated.resources.delete
import expenseapp.shared.generated.resources.delete_expense
import expenseapp.shared.generated.resources.description
import gal.orxeira.expenseapp.core.domain.model.Expense
import gal.orxeira.expenseapp.core.ui.utils.beautify
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConfirmDeleteDialog(
    expense: Expense,
    onDeleteConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(Res.string.delete_expense), style = MaterialTheme.typography.headlineSmall)
        },
        text = {
            Column {
                Text(
                    text = stringResource(Res.string.confirm_delete_expense),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${stringResource(Res.string.description)}: ${expense.description}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${stringResource(Res.string.amount)}: $${expense.amount}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${stringResource(Res.string.category)}: ${expense.category}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${stringResource(Res.string.date)}: ${expense.date.beautify()}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteConfirm()
                }
            ) {
                Text(text = stringResource(Res.string.delete), color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(Res.string.cancel))
            }
        }
    )
}