import androidx.lifecycle.LiveData
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Schedule
import ch.heigvd.iict.daa.template.dao.NoteDao
import ch.heigvd.iict.daa.template.dao.ScheduleDao

class DataRepository(private val noteDao: NoteDao, private val scheduleDao: ScheduleDao) {

    val allNotes: LiveData<List<NoteAndSchedule>> = noteDao.getAllNotes()
    val countNotes: LiveData<Int> = noteDao.countNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun insert(schedule: Schedule) {
        scheduleDao.insert(schedule)
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
}
