/**
 * Auteur : Koestli Camille / Oliveira Vitoria
 * Description : Ce fichier définit la classe `Note`, qui représente une note persistante
 * dans la base de données Room. Chaque note inclut un état, un titre, un texte, une date
 * de création et un type. Il propose également des méthodes pour générer des notes
 * et des plannings aléatoires.
 */

package ch.heigvd.iict.daa.labo4.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) var noteId : Long? = null,
    var state : State,
    var title : String,
    var text : String,
    var creationDate : Calendar,
    var type : Type
) {
    companion object {
        private val rand = Random()

        private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        private fun getRandomString(length: Int = 12) : String {
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }

        fun generateRandomNote() : Note {
            val _state = if(rand.nextBoolean()) State.IN_PROGRESS else State.DONE
            val _title = getRandomString(4 + rand.nextInt(10))
            val _text = getRandomString(8 + rand.nextInt(20))
            val _type = Type.values()[rand.nextInt(Type.values().size)]

            return Note(
                null,
                _state,
                _title,
                _text,
                Calendar.getInstance(),
                _type
            )
        }

        fun generateRandomSchedule() : Schedule? {
            if(rand.nextBoolean()) return null
            val inThePast = rand.nextDouble() > 0.9
            val sign = if(inThePast) -1 else 1
            return Schedule(null,
                    -1L, //tmp
                    Calendar.getInstance().apply {
                        add(Calendar.MONTH, sign * rand.nextInt(3))
                        add(Calendar.DAY_OF_MONTH, sign * rand.nextInt(30))
                    })
        }
    }
}