package app.cj.viewmodelexample.ui.main

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import app.cj.viewmodelexample.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    val TAG = this.javaClass.simpleName

    val ANIMATION_DURATION: Long = 500

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // initialisiert den Button
        this.initButton()

        // Viewmodel wird hier inizialisiert.
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Observer ist eine Art -- Watcher
        // wird die Variable geändert, wird der Observer gerufen
        viewModel.test.observe(viewLifecycleOwner, Observer { value ->
            // schreibt einen Log-Eintrag in den Device internen Log
            Log.d(TAG, "Get testvalue: $value")

            // setzt den neuen Wert
            tv_main_message.text = value

            // Animiert den Text
            tv_main_message.animate().alpha(1.0f).setDuration(ANIMATION_DURATION)
        })

        // Observer ist eine Art -- Watcher
        // wird die Variable geändert, wird der Observer gerufen
        viewModel.updateIsRunning.observe(viewLifecycleOwner, Observer { value ->
            if (value) {

                // clear Textfield
                tv_main_message.animate().alpha(0.0f).setDuration(ANIMATION_DURATION)
                    .setListener(object : AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) {

                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            if (tv_main_message.alpha == 0.0f)
                                tv_main_message.text = ""
                        }

                        override fun onAnimationCancel(animation: Animator?) {

                        }

                        override fun onAnimationRepeat(animation: Animator?) {

                        }
                    })

                // Zeigt den Status-Text und die Progressbar an
                // Das passiert als animation
                tv_loading_status.text = "Daten werden geladen..."
                pb_loading_data.animate().alpha(1.0f).setDuration(ANIMATION_DURATION)
                tv_loading_status.animate().alpha(1.0f).setDuration(ANIMATION_DURATION)

            } else {
                // Versteckt den Status-Text und die Progressbar
                // Das passiert als animation
                pb_loading_data.animate().alpha(0.0f).setDuration(ANIMATION_DURATION)
                tv_loading_status.animate().alpha(0.0f).setDuration(ANIMATION_DURATION)
            }
        })

        // Launcht automatisch beim start der App den Server upload.
        lifecycleScope.launch {
            viewModel.changeTest("155155")
        }
    }

    /**
     * Initialisiert den Button-Listener
     * sobald auf den Button gedrückt wird,
     * wird diese funktion ausgeführt
     */
    private fun initButton() {
        btn_start_upload.setOnClickListener {
            Log.d(TAG, "Button-Start-Upload")
            this.btnStartUploadHandleClick()
        }
    }

    /**
     * Diese funktion wir dann ausgelöst, wenn auf den Knopf
     * gedrückt wird
     */
    private fun btnStartUploadHandleClick() {
        // Server Upload
        lifecycleScope.launch {
            viewModel.changeTest("155155")
        }
    }
}