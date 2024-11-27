package ch.heigvd.iict.daa.template

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, FragmentNotes())
                .commit()
        }
        // todo add actionbar

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.notes_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as? FragmentNotes
        return when (item.itemId) {
            R.id.sort_creation_date_action -> {
                fragment?.sortNotesByCreationDate()
                true
            }
            R.id.sort_schedule_date_action -> {
                fragment?.sortNotesByScheduleDate()
                true
            }
            R.id.generate_note_action -> {
                fragment?.generateRandomNote()
                true
            }
            R.id.delete_all_notes_action -> {
                fragment?.deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    // todo complete
    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_date -> {
                // Call sorting logic (e.g., trigger a function in FragmentNotes)
                true
            }
            R.id.action_sort_by_type -> {
                // Call sorting logic (e.g., trigger a function in FragmentNotes)
                true
            }
            R.id.action_add_note -> {
                // Generate a new random note and add it
                addRandomNote()
                true
            }
            R.id.action_delete_all -> {
                // Delete all notes
                deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    */
}