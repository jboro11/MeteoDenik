package cz.utb.meteodenik

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

class MainActivity : SledovanaAktivita() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeteoTheme {
                var mesta by remember { mutableStateOf(vychoziMesta) }
                var dalsiId by remember { mutableStateOf(4) }
                var vstup by rememberSaveable { mutableStateOf("") }
                var duplicita by remember { mutableStateOf(false) }
                Scaffold { insets ->
                    MestaScreen(this@MainActivity, mesta, vstup, duplicita,
                        zmenaVstupu = { vstup = it; duplicita = false },
                        pridat = {
                            val jmeno = vstup.trim()
                            if (jmeno.isNotEmpty()) {
                                duplicita = mesta.any { it.nazev.equals(jmeno, ignoreCase = true) }
                                if (!duplicita) {
                                    mesta = mesta + Mesto(dalsiId++, jmeno, null, null)
                                    vstup = ""
                                }
                            }
                        },
                        odebrat = { id -> mesta = mesta.filterNot { it.id == id } },
                        detail = { id ->
                            if (vychoziMesta.any { it.id == id }) startActivity(DetailMestaActivity.intent(this@MainActivity, id))
                        },
                        modifier = Modifier.padding(insets)
                    )
                }
            }
        }
    }
}
