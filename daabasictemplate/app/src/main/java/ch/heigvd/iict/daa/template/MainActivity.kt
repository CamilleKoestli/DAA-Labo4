package ch.heigvd.iict.daa.template

import DataRepository
import NotesAdapter
import NotesViewModel
import NotesViewModelFactory
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


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