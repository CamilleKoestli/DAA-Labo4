/**
 * Auteur : Koestli Camille / Oliveira Vitoria
 * Description : Ce fichier définit l'interface `ScheduleDao`, qui fournit des méthodes
 * d'accès aux données pour manipuler les plannings (`Schedule`) dans la base de données Room.
 * Les méthodes incluent l'insertion et la suppression avec gestion des conflits.
 */

package ch.heigvd.iict.daa.template.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ch.heigvd.iict.daa.labo4.models.Schedule

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: Schedule)

    @Delete
    suspend fun delete(schedule: Schedule)
}