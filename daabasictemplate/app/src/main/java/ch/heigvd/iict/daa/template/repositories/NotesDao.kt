package ch.heigvd.iict.daa.template.repositories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Schedule

@Dao
interface NotesDao {

    @Transaction
    @Query("SELECT * FROM note")
    fun getAllNotes(): LiveData<List<NoteAndSchedule>>

    @Query("SELECT COUNT(*) FROM note")
    fun getNotesCount(): LiveData<Int>

    @Insert
    fun insertNote(note: Note): Long

    @Delete
    fun deleteAllNotes(note: Note)

    @Insert
    fun insertSchedule(schedule: Schedule): Long
}