package app.cj.viewmodelexample.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainModel {

    /**
     * Diese Funktion stellt eine Dateiupload dar. Hier dauert
     * der Dateiupload 3 sec
     */
    suspend fun updateTest(value : String) : String = withContext(Dispatchers.Default) {

        // ein async prozess, welcher auf einem anderen Thread laueft als der MAIN thread.
        // Hier wird ein background Prozess gestartet.
        val job = async {
            // wartet 3 sekunden
            delay(3000)

            // fügt dem übergebendem String ein value, damit wir
            // sicher gehen können damit wir diesen Part im
            // Quellcode auch wirklich erreicht haben
            var res = value + "_async_"

            // Hier wird der neue Wert zurückgegeben
            // job.await() -> wartet auf das result
            res
        }

        // hier wird auf das Ergebnis des "jobs" gewartete und zurückgegeben
        return@withContext job.await()
    }.toString()

}