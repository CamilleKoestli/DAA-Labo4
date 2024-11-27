/**
 * Auteur : Koestli Camille / Oliveira Vitoria
 * Description : Ce fichier définit la classe `NoteAndSchedule` qui permet de représenter
 * une relation entre une note et sa planification (schedule) dans la base de données Room.
 * La relation est établie via les colonnes `noteId` (note) et `ownerId` (schedule).
 */

package ch.heigvd.iict.daa.labo4.models

import androidx.room.Embedded
import androidx.room.Relation

data class NoteAndSchedule (
    @Embedded val note: Note,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "ownerId"
    )
    val schedule: Schedule?
)
