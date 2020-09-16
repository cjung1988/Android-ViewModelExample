package app.cj.viewmodelexample.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.cj.viewmodelexample.model.MainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    // Ein Beispiel value
    var test: MutableLiveData<String> = MutableLiveData("")

    // Diese Variable gibt zeigt den Status des "Dateiuploads"
    // wenn TRUE -> wird ein Ladebalken angezeigt
    // wenn FALSE -> wird der Ladebalken versteckt
    var updateIsRunning: MutableLiveData<Boolean> = MutableLiveData(false)

    // Hier wird das Mainmodel neu gebildet
    var model = MainModel()

    /**
     * suspend fun changeTest run on [Dispatchers.Default]
     * @param value [String]
     */
    suspend fun changeTest(value: String) = withContext(Dispatchers.Default) {
        // start progress bar
        updateIsRunning.postValue(true)

        // ruft die Updatefunktion auf
        // Dieses dauert LAAAANGE und gibt den neue String zurück
        val result = model.updateTest(value)

        // gibt den wert zurück an die UI
        test.postValue(result)

        // end progressbar
        updateIsRunning.postValue(false)
    }
}