package cz.utb.meteodenik

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class MainActivity : SledovanaAktivita() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold { insets ->
                    Column(Modifier.padding(insets).padding(24.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(stringResource(R.string.city_temperature, stringResource(R.string.title), pluralStringResource(R.plurals.city_count, vychoziMesta.size, vychoziMesta.size)), style = MaterialTheme.typography.headlineMedium)
                        PolohaKarta(this@MainActivity)
                        vychoziMesta.forEach { mesto ->
                            Button(onClick = { startActivity(DetailMestaActivity.intent(this@MainActivity, mesto.id)) }) {
                                Text(stringResource(R.string.city_temperature, mesto.nazev, stringResource(R.string.temperature, requireNotNull(mesto.teplota))))
                            }
                        }
                    }
                }
            }
        }
    }
}
