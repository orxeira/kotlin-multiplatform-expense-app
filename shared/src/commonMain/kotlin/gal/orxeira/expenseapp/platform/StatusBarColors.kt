package gal.orxeira.expenseapp.platform

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun StatusBarColors(
    statusBarColor: Color,
    navBarColor: Color,
    darkTheme: Boolean = isSystemInDarkTheme()
)