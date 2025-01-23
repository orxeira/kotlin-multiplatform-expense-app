package gal.orxeira.expenseapp.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import expenseapp.shared.generated.resources.Res
import expenseapp.shared.generated.resources.category_icon
import gal.orxeira.expenseapp.core.ui.theme.CatIconBackground
import gal.orxeira.expenseapp.core.ui.theme.NeutralsDark
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CategoryIcon(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
) {
    val defaultModifier = Modifier
        .size(60.dp)
        .padding(8.dp)
        .background(
            color = CatIconBackground,
            shape = RoundedCornerShape(percent = 15)
        )

    Box(
        modifier = modifier.then(defaultModifier),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = stringResource(Res.string.category_icon),
            tint = NeutralsDark
        )
    }
}