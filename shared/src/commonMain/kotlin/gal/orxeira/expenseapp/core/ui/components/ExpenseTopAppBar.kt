package gal.orxeira.expenseapp.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import expenseapp.shared.generated.resources.Res
import expenseapp.shared.generated.resources.chevron_left
import expenseapp.shared.generated.resources.content_description_back_navigation_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTopAppBar(
    modifier: Modifier = Modifier,
    hasBackNavigation: Boolean = true,
    onClickNavigateBack: (() -> Unit)? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
    ),
    title: String
) {
    TopAppBar(
        modifier = modifier,
        colors = colors,
        navigationIcon = {
            if (hasBackNavigation) {
                Box(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    IconButton(
                        onClick = { onClickNavigateBack?.invoke() },
                        modifier = Modifier
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onBackground,
                                shape = RoundedCornerShape(percent = 100)
                            )
                            .size(30.dp),
                    ) {
                        Icon(
                            modifier = Modifier,
                            painter = painterResource(Res.drawable.chevron_left),
                            contentDescription = stringResource(Res.string.content_description_back_navigation_button),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                )
            }
        },
    )
}