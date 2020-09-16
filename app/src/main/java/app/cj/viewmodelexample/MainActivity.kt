package app.cj.viewmodelexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.cj.viewmodelexample.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setzt das Layout der aktivity
        setContentView(R.layout.main_activity)

        // Wenn die App das erste mal gestartet wird,
        // ist die Variable savedInstanceState null
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}