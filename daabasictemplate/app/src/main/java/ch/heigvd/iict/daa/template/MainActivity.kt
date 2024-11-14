package ch.heigvd.iict.daa.template

import android.os.Bundle
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

    }

    // CHECK FUNCTIONS BELOW
    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu defined in res/menu/main_menu.xml
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle menu actions based on the item selected
        return when (item.itemId) {
            R.id.menu_sort_by_creation_date -> {
                // Action to sort notes by creation date
                // TODO: Implement sorting by creation date here
                true
            }
            R.id.menu_sort_by_schedule_date -> {
                // Action to sort notes by scheduled date
                // TODO: Implement sorting by scheduled date here
                true
            }
            R.id.menu_generate_note -> {
                // Action to generate a random note
                // TODO: Implement note generation logic here
                true
            }
            R.id.menu_delete_all_notes -> {
                // Action to delete all notes
                // TODO: Implement logic to delete all notes here
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
     */
}