package ch.heigvd.iict.daa.template

import DataRepository
import NotesViewModel
import NotesViewModelFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class FragmentControls : Fragment() {


    private val viewModel: NotesViewModel by viewModels {
        val application = requireActivity().application
        val database = (application as App).database
        val repository = DataRepository(database.noteDao(), application.applicationScope)
        NotesViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.controls_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get references to views
        val notesCounter = view.findViewById<TextView>(R.id.notes_counter)
        val buttonGenerate = view.findViewById<Button>(R.id.button_generate)
        val buttonDeleteAll = view.findViewById<Button>(R.id.button_delete_all)

        // Observe the LiveData for note count
        viewModel.notesCounter.observe(viewLifecycleOwner) { count ->
            notesCounter.text = count.toString()
        }

        // Set up button click listeners
        buttonGenerate.setOnClickListener {
            viewModel.generateANote()
        }

        buttonDeleteAll.setOnClickListener {
            viewModel.deleteAllNotes()
        }
    }
}
