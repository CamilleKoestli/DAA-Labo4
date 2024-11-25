import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.Type
import ch.heigvd.iict.daa.template.R

sealed class NoteItem {
    data class SimpleNote(val note: Note) : NoteItem()
    data class NoteAndSchedule(val noteAndSchedule: ch.heigvd.iict.daa.labo4.models.NoteAndSchedule) : NoteItem()
}

class NotesAdapter(
    private val _noteItems: List<NoteItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var noteItems = listOf<NoteItem>()
        set(value) {
            val diffCallback = NoteDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    init { noteItems = _noteItems }

    override fun getItemCount()= noteItems.size

    // Constants to represent the view types
    companion object {
        private const val NOTE = 0
        private const val NOTE_WITH_SCHEDULE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NOTE -> NoteViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
            )

            else -> NoteAndScheduleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.note_and_schedule_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoteViewHolder -> holder.bind(noteItems[position] as NoteItem.SimpleNote)
            is NoteAndScheduleViewHolder -> holder.bind(noteItems[position] as NoteItem.NoteAndSchedule)
        }
    }


    /*project uses two distinct layouts (note_item and note_with_schedule_item), which means:

    SimpleNote and NoteAndSchedule cannot share a single ViewHolder.
    Each layout must have its own ViewHolder because the views (note_schedule_date, for example) are not shared.*/


    // ViewHolder for SimpleNote
    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val noteTypeIcon: ImageView = view.findViewById(R.id.note_type_icon)
        private val noteTitle: TextView = view.findViewById(R.id.note_title)
        private val noteText: TextView = view.findViewById(R.id.note_text)
        private val noteStateIcon: ImageView = view.findViewById(R.id.note_state_icon)
        private val noteStateText: TextView = view.findViewById(R.id.note_state_text)

        fun bind(noteItem: NoteItem.SimpleNote) {
            val note = noteItem.note
            noteTitle.text = note.title
            noteText.text = note.text

            // Set icon based on type
            val typeIcon = when (note.type) {
                Type.TODO -> R.drawable.todo
                Type.SHOPPING -> R.drawable.shopping
                Type.WORK -> R.drawable.work
                Type.FAMILY -> R.drawable.family
                Type.NONE -> R.drawable.note
            }
            noteTypeIcon.setImageResource(typeIcon)
            noteStateIcon.setImageResource(R.drawable.clock)

            // Set state icon and text   todo
            /*val stateIconRes = if (note.state.name == "IN_PROGRESS") {
                R.drawable.clock
            } else {
                R.drawable.ic_done
            }*/
            noteStateText.text = note.state.name
        }
    }

    // ViewHolder for NoteAndSchedule
    inner class NoteAndScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val noteTypeIcon: ImageView = view.findViewById(R.id.note_type_icon)
        private val noteTitle: TextView = view.findViewById(R.id.note_title)
        private val noteText: TextView = view.findViewById(R.id.note_text)
        private val noteStateIcon: ImageView = view.findViewById(R.id.note_state_icon)
        private val noteStateText: TextView = view.findViewById(R.id.note_state_text)
        private val noteScheduleDate: TextView = view.findViewById(R.id.note_state_text)

        fun bind(noteItem: NoteItem.NoteAndSchedule) {
            val noteAndSchedule = noteItem.noteAndSchedule
            val note = noteAndSchedule.note
            noteTitle.text = note.title
            noteText.text = note.text

            // Set icon based on type
            val typeIcon = when (note.type) {
                Type.TODO -> R.drawable.todo
                Type.SHOPPING -> R.drawable.shopping
                Type.WORK -> R.drawable.work
                Type.FAMILY -> R.drawable.family
                Type.NONE -> R.drawable.note
            }
            noteTypeIcon.setImageResource(typeIcon)
            noteStateIcon.setImageResource(R.drawable.clock)
            noteStateText.text = note.state.name

            // Bind schedule-specific data
            noteScheduleDate.text = noteAndSchedule.schedule?.date?.time.toString()
        }
    }
}


class NoteDiffCallback(private val oldList: List<NoteItem>, private val newList: List<NoteItem>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old::class == new::class
    }
}


