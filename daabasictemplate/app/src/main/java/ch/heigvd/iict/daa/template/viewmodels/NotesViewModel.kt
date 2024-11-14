import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: DataRepository) : ViewModel() {

    val allNotes: LiveData<List<NoteAndSchedule>> = repository.allNotes
    val countNotes: LiveData<Int> = repository.countNotes

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
