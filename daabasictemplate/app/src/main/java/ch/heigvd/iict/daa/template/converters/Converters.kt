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
