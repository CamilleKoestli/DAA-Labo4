/**
 * Auteur : Koestli Camille / Oliveira Vitoria
 * Description : Ce fichier définit le DataRepository, une couche intermédiaire entre les DAO de la
 * base de données Room et le ViewModel. Il fournit des méthodes pour gérer les opérations CRUD sur
 * les notes et leurs plannings, telles que l'insertion, la suppression, et le tri. Le repository utilise
 * également une coroutine pour générer et insérer des notes aléatoires.
 */

import androidx.lifecycle.LiveData
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.dao.NoteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DataRepository(private val noteDao: NoteDao, private val applicationScope: CoroutineScope) {

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

    // pour générer une note aléatoire et la mettre dans la DB
    suspend fun generateRandomNote() {
        val note = Note.generateRandomNote()
        val schedule = Note.generateRandomSchedule()
        val id = noteDao.insert(note)
        schedule?.let {
            it.ownerId = id
            noteDao.insert(it)
        }
    }

    fun sortByCreationDate() {
        allNotes = noteDao.getAllNotesSortedByCreationDate()
    }

    fun sortByScheduleDate() {
        allNotes = noteDao.getAllNotesSortedByScheduleDate()
    }
}
