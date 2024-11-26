package ch.heigvd.iict.daa.template

import android.app.Application

class App : Application() {
    val database: NotesDatabase by lazy { NotesDatabase.getDatabase(this) }
}