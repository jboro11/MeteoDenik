package cz.utb.meteodenik

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable private data object Prehled
@Serializable private data class Detail(val id: Int)

class MainActivity : SledovanaAktivita() {
    private val model: MestaViewModel by viewModels {
        viewModelFactory { initializer { MestaViewModel(createSavedStateHandle()) } }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val stav by model.stav.collectAsStateWithLifecycle()
            val navigace = rememberNavController()
            MeteoTheme {
                Scaffold { insets ->
                    NavHost(navigace, startDestination = Prehled, modifier = Modifier.padding(insets)) {
                        composable<Prehled> {
                            MestaScreen(
                                this@MainActivity, stav.mesta, stav.rozepsane, stav.duplicita,
                                model::zmenaVstupu, model::pridat, model::odebrat,
                                detail = { id -> navigace.navigate(Detail(id)) { launchSingleTop = true } }
                            )
                        }
                        composable<Detail> { zaznam ->
                            val id = zaznam.toRoute<Detail>().id
                            val mesto = stav.mesta.firstOrNull { it.id == id }
                            if (mesto == null) Text(stringResource(R.string.empty))
                            else DetailMesta(mesto, { navigace.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
