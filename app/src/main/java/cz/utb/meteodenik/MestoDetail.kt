package cz.utb.meteodenik

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun DetailMesta(mesto: Mesto, zpet: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(mesto.nazev, style = MaterialTheme.typography.headlineMedium)
        Text(mesto.teplota?.let { stringResource(R.string.temperature, it) }
            ?: stringResource(R.string.missing_temperature))
        Button(onClick = zpet) { Text(stringResource(R.string.back)) }
    }
}
