/**
 * Auteur : Koestli Camille / Oliveira Vitoria
 * Description : Ce fichier définit l'entité `Schedule` utilisée pour représenter la planification
 * associée à une note dans la base de données. Chaque planification est identifiée par un ID unique,
 * est liée à une note par `ownerId` et inclut une date de type `Calendar`.
 */

package ch.heigvd.iict.daa.labo4.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Schedule(
    @PrimaryKey(autoGenerate = true) var scheduleId : Long?,
    var ownerId : Long,
    var date : Calendar
)