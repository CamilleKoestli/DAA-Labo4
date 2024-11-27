import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: DataRepository) : ViewModel() {

    val allNotes: LiveData<List<NoteAndSchedule>> = repository.allNotes
    val notesCounter: LiveData<Int> = repository.notesCounter

    fun sortNotesByCreationDate() {
        viewModelScope.launch {
            repository.sortByCreationDate()
        }
    }

    fun sortNotesByScheduleDate() {
        viewModelScope.launch {
            repository.sortByScheduleDate()
        }
    }

    fun generateANote() {
        viewModelScope.launch {
            repository.generateRandomNote()
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }
}


class NotesViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}