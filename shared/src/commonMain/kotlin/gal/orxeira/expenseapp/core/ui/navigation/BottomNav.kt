package gal.orxeira.expenseapp.core.ui.navigation

import expenseapp.shared.generated.resources.Res
import expenseapp.shared.generated.resources.about
import expenseapp.shared.generated.resources.home
import expenseapp.shared.generated.resources.home_filled
import expenseapp.shared.generated.resources.home_outlined
import expenseapp.shared.generated.resources.info_filled
import expenseapp.shared.generated.resources.info_outlined
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class BottomNav(
    val labelRes: StringResource,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource,
    val index: Int,
    val route: Any,
) {
    Home(
        labelRes = Res.string.home,
        selectedIcon = Res.drawable.home_filled,
        unselectedIcon = Res.drawable.home_outlined,
        route = Screens.Home,
        index = 0,
    ),
    Calendar(
        labelRes = Res.string.about,
        selectedIcon = Res.drawable.info_filled,
        unselectedIcon = Res.drawable.info_outlined,
        route = Screens.About,
        index = 1,
    )
}