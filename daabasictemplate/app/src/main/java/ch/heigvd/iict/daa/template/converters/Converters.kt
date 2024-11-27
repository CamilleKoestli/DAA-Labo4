/**
 * Auteur : Koestli Camille / Oliveira Vitoria
 * Description : Ce fichier contient la classe `Converters`, qui définit des convertisseurs
 * (`TypeConverter`) pour permettre à Room de gérer des types complexes tels que `Calendar`
 * et `Type`. Ces convertisseurs transforment ces types en formats simples comme `Long` ou `String`
 * pour le stockage, et permettent leur reconversion lors de la récupération.
 */

package ch.heigvd.iict.daa.template.converters
import androidx.room.TypeConverter
import ch.heigvd.iict.daa.labo4.models.Type
import java.util.Calendar
import java.util.Date

class Converters {

    // Convert Calendar to Long (timestamp)
    @TypeConverter
    fun fromCalendar(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }

    // Convert Long (timestamp) to Calendar
    @TypeConverter
    fun toCalendar(timestamp: Long?): Calendar? {
        return timestamp?.let {
            Calendar.getInstance().apply {
                timeInMillis = it
            }
        }
    }

    // Convert Enum to String
    @TypeConverter
    fun fromType(type: Type?): String? {
        return type?.name
    }

    // Convert String to Enum
    @TypeConverter
    fun toType(name: String?): Type? {
        return name?.let { Type.valueOf(it) }
    }
}
