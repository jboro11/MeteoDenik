package cz.utb.meteodenik

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class DetailMestaActivity : SledovanaAktivita() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mesto = vychoziMesta.firstOrNull { it.id == intent.getIntExtra(KEY_CITY, -1) }
            ?: return finish()
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold { insets -> DetailMesta(mesto, { finish() }, Modifier.padding(insets)) }
            }
        }
    }

    companion object {
        private const val KEY_CITY = "mesto"

        fun intent(context: Context, id: Int): Intent =
            Intent(context, DetailMestaActivity::class.java).putExtra(KEY_CITY, id)
    }
}

@Composable
fun DetailMesta(mesto: Mesto, zpet: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(mesto.nazev, style = MaterialTheme.typography.headlineMedium)
        Text(mesto.teplota?.let { stringResource(R.string.temperature, it) }
            ?: stringResource(R.string.missing_temperature))
        Button(onClick = zpet) { Text(stringResource(R.string.back)) }
    }
}
