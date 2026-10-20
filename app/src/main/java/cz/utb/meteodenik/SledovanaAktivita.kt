package cz.utb.meteodenik

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity

abstract class SledovanaAktivita : ComponentActivity() {
    private fun zapis(udalost: String) {
        Log.d("MeteoCyklus", "${javaClass.simpleName}: $udalost")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zapis("onCreate (obnova=${savedInstanceState != null})")
    }

    override fun onStart() {
        super.onStart()
        zapis("onStart")
    }

    override fun onResume() {
        super.onResume()
        zapis("onResume")
    }

    override fun onPause() {
        super.onPause()
        zapis("onPause")
    }

    override fun onStop() {
        super.onStop()
        zapis("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        zapis("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        zapis("onDestroy (rotace=$isChangingConfigurations)")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        zapis("onSaveInstanceState")
    }
}
