import androidx.lifecycle.LiveData
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.dao.NoteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DataRepository(private val noteDao: NoteDao, private val applicationScope: CoroutineScope) {

    var allNotes: LiveData<List<NoteAndSchedule>> = noteDao.getAllNotes()
    var notesCounter: LiveData<Int> = noteDao.countNotes()

    // Méthode pour insérer une note
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    // Méthode pour supprimer une note
    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    // Méthode pour supprimer toutes les notes
    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    // Méthode pour générer une note aléatoire et l'insérer dans la base
    suspend fun generateRandomNote() {
        val note = Note.generateRandomNote()
        val schedule = Note.generateRandomSchedule()
        val id = noteDao.insert(note) // Assurez-vous que noteDao est bien initialisé dans DataRepository
        schedule?.let {
            it.ownerId = id
            noteDao.insert(it)
        }
    }

    // Méthode pour trier les notes par date de création
    fun sortByCreationDate() {
        allNotes = noteDao.getAllNotesSortedByCreationDate()
    }

    // Méthode pour trier les notes par date prévue
    fun sortByScheduleDate() {
        allNotes = noteDao.getAllNotesSortedByScheduleDate()
    }
}
