package gal.orxeira.expenseapp.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import expenseapp.shared.generated.resources.Res
import expenseapp.shared.generated.resources.chevron_left
import expenseapp.shared.generated.resources.chevron_right
import expenseapp.shared.generated.resources.next_week
import expenseapp.shared.generated.resources.previous_week
import gal.orxeira.expenseapp.core.ui.utils.today
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExpenseDatePicker(
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit = {},
    date: LocalDate? = null,
    isEnabled: Boolean = true,
) {
    var currentDate by remember { mutableStateOf(date ?: today().date) }
    val currentWeek = remember(currentDate) { getWeekDates(currentDate) }

    Column(
        modifier = modifier
            .wrapContentSize()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(percent = 10)
            )
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        // Row 1: Month-Year and Arrows
        Row(
            modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                modifier = Modifier.border(
                    1.dp,
                    MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(16.dp)
                ),
                onClick = {
                    if (isEnabled) {
                        currentDate = currentDate.minus(1, DateTimeUnit.WEEK)
                    }
                }) {
                Icon(
                    painter = painterResource(Res.drawable.chevron_left),
                    contentDescription = stringResource(Res.string.previous_week),
                    tint = Color.Black
                )
            }

            Text(
                text = "${currentDate.month.name.take(3)} - ${currentDate.year}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )

            IconButton(
                modifier = Modifier.border(
                    1.dp,
                    MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(16.dp)
                ),
                onClick = {
                    if (isEnabled) {
                        currentDate = currentDate.plus(1, DateTimeUnit.WEEK)
                    }
                }) {
                Icon(
                    painter = painterResource(Res.drawable.chevron_right),
                    contentDescription = stringResource(Res.string.next_week),
                    tint = Color.Black
                )
            }
        }

        // Row 2: Days of the Week
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            currentWeek.forEach { day ->
                Box(
                    modifier = Modifier
                        .weight(1f) // Each item takes equal weight
                        .padding(horizontal = 4.dp), // Optional padding for spacing
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = day.dayOfWeek.name.take(2),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }

        // Row 3: Days of the Month
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            currentWeek.forEach { day ->
                DayButton(
                    day = day,
                    isSelected = day == currentDate,
                    isCurrentMonth = day.month == currentDate.month,
                    onClick = {
                        if (isEnabled) {
                            currentDate = day
                            onDateSelected(day)
                        }
                    })
            }
        }
    }
}

@Composable
fun DayButton(
    day: LocalDate,
    isSelected: Boolean,
    onClick: () -> Unit,
    isCurrentMonth: Boolean
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(8.dp)
            )

            .sizeIn(maxHeight = 40.dp, maxWidth = 40.dp),
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "${day.dayOfMonth}",
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            modifier = Modifier.alpha(if (isCurrentMonth) 1f else 0.5f),
        )
    }
}

fun getWeekDates(currentDate: LocalDate): List<LocalDate> {
    // Get the first day of the week (we'll assume Monday as the start of the week)
    val startOfWeek = currentDate.minus(currentDate.dayOfWeek.ordinal, DateTimeUnit.DAY)

    // Generate a list of 7 days from the start of the week
    return (0 until 7).map { number -> startOfWeek.plus(number, DateTimeUnit.DAY) }
}