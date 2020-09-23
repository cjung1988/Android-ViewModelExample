package app.cj.viewmodelexample.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import app.cj.viewmodelexample.database.AppDatabase
import app.cj.viewmodelexample.model.MainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainViewModel(app : Application) : AndroidViewModel(app) {

    // Ein Beispiel value
    var test: MutableLiveData<String> = MutableLiveData("")

    // Diese Variable gibt zeigt den Status des "Dateiuploads"
    // wenn TRUE -> wird ein Ladebalken angezeigt
    // wenn FALSE -> wird der Ladebalken versteckt
    var updateIsRunning: MutableLiveData<Boolean> = MutableLiveData(false)

    // Hier wird das Mainmodel neu gebildet
    var model = MainModel()

    val db = Room.databaseBuilder(
        getApplication(),
        AppDatabase::class.java, "database-name"
    ).build()

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

        var test = db.userDao().getAll()
    }
}