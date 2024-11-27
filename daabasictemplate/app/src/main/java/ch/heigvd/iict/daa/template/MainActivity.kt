/**
 * Auteur : Koestli Camille / Oliveira Vitoria
 * Description : Ce fichier définit la classe MainActivity, qui agit comme un conteneur principal de l'application.
 * Elle gère les fragments (Notes et Contrôles), configure les menus pour les actions globales (tri, suppression, génération),
 * et adapte l'affichage en fonction du type d'appareil (téléphone ou tablette).
 */

package ch.heigvd.iict.daa.template

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView

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
            if(findViewById<FragmentContainerView>(R.id.fragment_container_view) != null) {
                // Téléphone
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, FragmentNotes())
                    .commit()
            } else {
                // Tablette
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_notes, FragmentNotes())
                    .replace(R.id.container_controls, FragmentControls())
                    .commit()
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as? FragmentNotes
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
                fragment?.sortNotesByScheduleDate()
                true
            }
            R.id.main_menu_creation -> {
                fragment?.sortNotesByCreationDate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
