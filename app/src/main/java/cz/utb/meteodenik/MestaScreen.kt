package cz.utb.meteodenik

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun MestaScreen(
    activity: ComponentActivity,
    mesta: List<Mesto>,
    rozepsane: String,
    duplicita: Boolean,
    zmenaVstupu: (String) -> Unit,
    pridat: () -> Unit,
    odebrat: (Int) -> Unit,
    detail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    dalsiObsah: @Composable () -> Unit = {}
) {
    LazyColumn(
        modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                stringResource(R.string.reading_count, stringResource(R.string.title), pluralStringResource(R.plurals.city_count, mesta.size, mesta.size)),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top) {
                OutlinedTextField(
                    value = rozepsane,
                    onValueChange = zmenaVstupu,
                    label = { Text(stringResource(R.string.city_input)) },
                    singleLine = true,
                    isError = duplicita,
                    supportingText = if (duplicita) ({ Text(stringResource(R.string.duplicate_city)) }) else null,
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = pridat, enabled = rozepsane.isNotBlank(), modifier = Modifier.padding(top = 8.dp)) {
                    Text(stringResource(R.string.add_city))
                }
            }
        }
        item { dalsiObsah() }
        if (mesta.isEmpty()) item { Text(stringResource(R.string.empty)) }
        items(mesta, key = Mesto::id) { mesto ->
            Card(
                onClick = { detail(mesto.id) },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(mesto.nazev, style = MaterialTheme.typography.titleMedium)
                        Text(mesto.teplota?.let { stringResource(R.string.temperature, it) }
                            ?: stringResource(R.string.missing_temperature))
                    }
                    TextButton(onClick = { odebrat(mesto.id) }) { Text(stringResource(R.string.remove_city)) }
                }
            }
        }
        item { PolohaKarta(activity) }
    }
}
