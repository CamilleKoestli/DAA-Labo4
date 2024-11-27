/**
 * Auteur : Koestli Camille / Oliveira Vitoria
 * Description : Ce fichier définit l'interface `NoteDao`, qui fournit des méthodes d'accès
 * aux données pour manipuler les notes (`Note`) et leurs relations avec les plannings
 * (`Schedule`) dans la base de données Room. Les méthodes incluent des requêtes pour
 * récupérer, trier et compter les notes, ainsi que pour insérer ou supprimer des éléments.
 */

package ch.heigvd.iict.daa.template.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Schedule

@Dao
interface NoteDao {

    @Transaction
    @Query("SELECT * FROM note")
    fun getAllNotes(): LiveData<List<NoteAndSchedule>>

    @Transaction
    @Query("""
        SELECT * 
        FROM note 
        LEFT JOIN schedule 
        ON note.noteId = schedule.ownerId 
        ORDER BY schedule.date ASC
    """)
    fun getAllNotesSortedByScheduleDate(): LiveData<List<NoteAndSchedule>>

    @Transaction
    @Query("""
        SELECT * 
        FROM note 
        LEFT JOIN schedule 
        ON note.noteId = schedule.ownerId 
        ORDER BY note.creationDate ASC
    """)
    fun getAllNotesSortedByCreationDate(): LiveData<List<NoteAndSchedule>>

    @Query("SELECT COUNT(*) FROM note")
    fun countNotes(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Insert
    suspend fun insert(schedule: Schedule)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM Note")
    suspend fun deleteAllNotes()

}