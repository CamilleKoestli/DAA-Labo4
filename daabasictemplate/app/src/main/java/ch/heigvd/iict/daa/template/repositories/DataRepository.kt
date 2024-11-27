import androidx.lifecycle.LiveData
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.dao.NoteDao

class DataRepository(private val noteDao: NoteDao) {

    var allNotes: LiveData<List<NoteAndSchedule>> = noteDao.getAllNotes()
    var notesCounter: LiveData<Int> = noteDao.countNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    // Méthode pour générer une note aléatoire et l'insérer dans la base
    suspend fun generateRandomNote() {
        val randomNote = Note.generateRandomNote()
        noteDao.insert(randomNote)
    }

    fun sortByCreationDate() {
        allNotes = noteDao.getAllNotesSortedByCreationDate()
    }

    fun sortByScheduleDate() {
        allNotes = noteDao.getAllNotesSortedByScheduleDate()
    }

}
