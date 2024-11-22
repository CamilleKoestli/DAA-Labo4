import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Type
import ch.heigvd.iict.daa.template.R

sealed class NoteItem {
    data class SimpleNote(val note: Note) : NoteItem()
    data class NoteWithSchedule(val noteAndSchedule: NoteAndSchedule) : NoteItem()
}

class NotesAdapter(
    private val noteItems: List<NoteItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Constants to represent the view types
    companion object {
        private const val NOTE = 0
        private const val NOTE_WITH_SCHEDULE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (noteItems[position]) {
            is NoteItem.SimpleNote -> NOTE
            is NoteItem.NoteWithSchedule -> NOTE_WITH_SCHEDULE
            else -> {
                throw IllegalArgumentException("Invalid type of data $position")}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == NOTE ) {
            RecyclerView.ViewHolder(NS_ViewBinding.inflate(inflater, parent, false))
        } else {
            RecyclerView.ViewHolder(NS_ViewBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = noteItems[position]) {
            is NoteItem.SimpleNote -> {
                if (holder is NoteViewHolder) holder.bind(item.note)
            }
            is NoteItem.NoteWithSchedule -> {
                if (holder is NoteWithScheduleViewHolder) holder.bind(item.noteAndSchedule)
            }

            else -> {
                throw IllegalArgumentException("Invalid type of data $position")
            }
        }
    }

    override fun getItemCount(): Int = noteItems.size

    // ViewHolder for Simple Notes
    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon: ImageView = view.findViewById(R.id.note_type_icon)
        private val title: TextView = view.findViewById(R.id.note_title)
        private val text: TextView = view.findViewById(R.id.note_text)

        fun bind(note: Note) {
            if(note is Note){
                title.text = note.title
                text.text = note.text

                // Set icon based on type
                val iconRes = when (note.type) {
                    Type.TODO -> R.drawable.todo
                    Type.SHOPPING -> R.drawable.shopping
                    Type.WORK -> R.drawable.work
                    Type.FAMILY -> R.drawable.family
                    Type.NONE -> R.drawable.none
                }
                icon.setImageResource(iconRes)
            }
        }
    }

    // ViewHolder for Notes with Schedule
    inner class NoteWithScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.note_type_icon)
        private val title: TextView = itemView.findViewById(R.id.note_title)
        private val text: TextView = itemView.findViewById(R.id.note_text)
        private val schedule: TextView = itemView.findViewById(R.id.note_state_text)

        fun bind(noteAndSchedule: NoteAndSchedule) {
            val note = noteAndSchedule.note
            title.text = note.title
            text.text = note.text
            schedule.text = noteAndSchedule.schedule?.date?.time.toString() // Format as needed

            // Set icon based on type
            val iconRes = when (note.type) {
                Type.TODO -> R.drawable.todo
                Type.SHOPPING -> R.drawable.shopping
                Type.WORK -> R.drawable.work
                Type.FAMILY -> R.drawable.family
                Type.NONE ->
            }
            icon.setImageResource(iconRes)
        }
    }
}