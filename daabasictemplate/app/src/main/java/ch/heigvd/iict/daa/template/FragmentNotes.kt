package ch.heigvd.iict.daa.template

import NotesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentNotes : Fragment() {

    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        adapter = NotesAdapter(emptyList())
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        // Example: Update the adapter with a sample list of notes
        /*adapter.noteItems = listOf(
            NoteItem.SimpleNote(Note(1, "TODO", "Example Note", ...)),
        NoteItem.NoteAndSchedule(NoteAndSchedule(...))
        )*/
    }
}
