package ch.heigvd.iict.daa.template

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ch.heigvd.iict.daa.template.dao.NoteDao

abstract class NotesDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null


        fun getDatabase(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}