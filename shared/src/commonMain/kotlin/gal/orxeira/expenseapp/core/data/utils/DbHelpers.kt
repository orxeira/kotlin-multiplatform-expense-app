package gal.orxeira.expenseapp.core.data.utils

import app.cash.sqldelight.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.JvmOverloads


@JvmOverloads
fun <T : Any> Flow<Query<T>>.mapToList(
    context: CoroutineContext = Dispatchers.Main.immediate,
): Flow<List<T>> = map {
    withContext(context) {
        it.executeAsList()
    }
}

@JvmOverloads
fun <T : Any> Flow<Query<T>>.mapToOneOrNull(
    context: CoroutineContext = Dispatchers.Default,
): Flow<T?> = map {
    withContext(context) {
        it.executeAsOneOrNull()
    }
}
