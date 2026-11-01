package cz.utb.meteodenik

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

private const val POLOHA = Manifest.permission.ACCESS_COARSE_LOCATION
private enum class StavPolohy { POPRVE, VYSVETLENI, NASTAVENI, POVOLENO }

@Composable
fun PolohaKarta(activity: ComponentActivity, modifier: Modifier = Modifier) {
    val prefs = remember(activity) { activity.getSharedPreferences("opravneni", 0) }
    var revize by remember { mutableIntStateOf(0) }
    var dialog by remember { mutableStateOf(false) }
    val zadost = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { revize++ }
    DisposableEffect(activity) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) revize++
        }
        activity.lifecycle.addObserver(observer)
        onDispose { activity.lifecycle.removeObserver(observer) }
    }
    val stav = remember(activity, revize) {
        when {
            ContextCompat.checkSelfPermission(activity, POLOHA) == PackageManager.PERMISSION_GRANTED -> StavPolohy.POVOLENO
            activity.shouldShowRequestPermissionRationale(POLOHA) -> StavPolohy.VYSVETLENI
            prefs.getBoolean("poloha_zadana", false) -> StavPolohy.NASTAVENI
            else -> StavPolohy.POPRVE
        }
    }
    fun pozadej() {
        prefs.edit().putBoolean("poloha_zadana", true).apply()
        zadost.launch(POLOHA)
    }
    Card(modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(stringResource(R.string.location_title))
            when (stav) {
                StavPolohy.POPRVE -> TextButton(onClick = ::pozadej) { Text(stringResource(R.string.location_request)) }
                StavPolohy.VYSVETLENI -> TextButton(onClick = { dialog = true }) {
                    Text(stringResource(R.string.location_request))
                }
                StavPolohy.NASTAVENI -> {
                    Text(stringResource(R.string.location_off))
                    TextButton(onClick = {
                        activity.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", activity.packageName, null)))
                    }) { Text(stringResource(R.string.settings)) }
                }
                StavPolohy.POVOLENO -> Text(stringResource(R.string.location_on))
            }
        }
    }
    if (dialog) {
        AlertDialog(
            onDismissRequest = { dialog = false },
            title = { Text(stringResource(R.string.location_title)) },
            text = { Text(stringResource(R.string.location_why)) },
            confirmButton = {
                TextButton(onClick = { dialog = false; pozadej() }) { Text(stringResource(R.string.continue_label)) }
            },
            dismissButton = {
                TextButton(onClick = { dialog = false }) { Text(stringResource(R.string.cancel)) }
            }
        )
    }
}
