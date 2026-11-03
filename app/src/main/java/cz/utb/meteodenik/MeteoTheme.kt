package cz.utb.meteodenik

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val svetle = lightColorScheme(
    primary = Color(0xFF245E51),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCAE9DE),
    onPrimaryContainer = Color(0xFF102F28),
    surface = Color(0xFFF6F9F6)
)
private val tmave = darkColorScheme(
    primary = Color(0xFFAADECC),
    onPrimary = Color(0xFF12352D),
    primaryContainer = Color(0xFF214A41),
    onPrimaryContainer = Color(0xFFD0EEE4),
    surface = Color(0xFF151C1B)
)

@Composable
fun MeteoTheme(dark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (dark) tmave else svetle, content = content)
}

@Composable
fun Vizitka(modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(Modifier.padding(20.dp)) {
            Text(stringResource(R.string.student_name), style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.student_program), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VizitkaSvetla() = MeteoTheme(dark = false) { Vizitka() }

@Preview(showBackground = true)
@Composable
private fun VizitkaTmava() = MeteoTheme(dark = true) { Vizitka() }
