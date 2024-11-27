package ch.heigvd.iict.daa.template

import android.app.Application
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.State
import ch.heigvd.iict.daa.labo4.models.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Calendar

class App : Application() {

    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database: NotesDatabase by lazy { NotesDatabase.getDatabase(this) }

    private fun prepopulateDatabase(database: NotesDatabase) {
        applicationScope.launch {
            val noteDao = database.noteDao()
            try {
                noteDao.insert(
                    Note(
                        noteId = null, // Auto-generate ID
                        title = "Test Note",
                        text = "This is a test note.",
                        type = Type.TODO,
                        state = State.IN_PROGRESS,
                        creationDate = Calendar.getInstance()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        prepopulateDatabase(database)
    }
}
