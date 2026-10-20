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
                        Text(stringResource(R.string.title), style = MaterialTheme.typography.headlineMedium)
                        vychoziMesta.forEach { mesto ->
                            Button(onClick = { startActivity(DetailMestaActivity.intent(this@MainActivity, mesto.id)) }) {
                                Text("${mesto.nazev} · " + stringResource(R.string.temperature, requireNotNull(mesto.teplota)))
                            }
                        }
                    }
                }
            }
        }
    }
}
