package ch.heigvd.iict.daa.template

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure l'ActionBar
        supportActionBar?.apply {
            title = getString(R.string.app_name) // Titre de l'application
        }

        // Charge le fragment initial si aucun état sauvegardé
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, FragmentNotes())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notes_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Récupère le fragment actif
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as? FragmentNotes

        // Gère les actions du menu
        return when (item.itemId) {
            R.id.main_menu_deleteAll -> {
                fragment?.deleteAllNotes()
                true
            }
            R.id.main_menu_generate -> {
                fragment?.generateRandomNote()
                true
            }
            R.id.main_menu_eta -> {
                fragment?.sortNotesByScheduleDate() // Correction de l'appel à la méthode
                true
            }
            R.id.main_menu_creation -> {
                fragment?.sortNotesByCreationDate() // Correction de l'appel à la méthode
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
