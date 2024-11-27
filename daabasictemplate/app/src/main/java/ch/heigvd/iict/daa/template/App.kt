package ch.heigvd.iict.daa.template

import android.app.Application
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.State
import ch.heigvd.iict.daa.labo4.models.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

class App : Application() {
    val database: NotesDatabase by lazy { NotesDatabase.getDatabase(this) }

    fun prepopulateDatabase(database: NotesDatabase) {
        val noteDao = database.noteDao()
        CoroutineScope(Dispatchers.IO)
            .launch {
            noteDao.insert(
                Note(
                    noteId = null, // Allow Room to auto-generate the ID
                    title = "Test Note",
                    text = "This is a test note.",
                    type = Type.TODO,
                    state = State.IN_PROGRESS,
                    creationDate = Calendar.getInstance()
                )
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        prepopulateDatabase(database)
    }
}