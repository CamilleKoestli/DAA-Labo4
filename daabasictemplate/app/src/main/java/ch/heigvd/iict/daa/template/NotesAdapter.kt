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
import java.util.Calendar
import java.util.Locale

sealed class NoteItem {
    data class SimpleNote(val note: Note) : NoteItem()
    data class NoteAndSchedule(val noteAndSchedule: ch.heigvd.iict.daa.labo4.models.NoteAndSchedule) : NoteItem()
}

class NotesAdapter(private val _noteItems: List<NoteItem>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var noteItems = listOf<NoteItem>()
        set(value) {
            val diffCallback = NoteDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    init {
        noteItems = _noteItems
    }

    override fun getItemCount() = noteItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // Inflate the simplified layout for all items
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteItems[position])
    }

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val noteTypeIcon: ImageView = view.findViewById(R.id.note_type_icon)
        private val noteTitle: TextView = view.findViewById(R.id.note_title)
        private val noteText: TextView = view.findViewById(R.id.note_text)
        private val scheduleClockImage: ImageView = view.findViewById(R.id.scheduleClockImage)
        private val scheduleMonths: TextView = view.findViewById(R.id.scheduleMonths)

        fun bind(noteItem: NoteItem) {
            when (noteItem) {
                is NoteItem.SimpleNote -> {
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

                    // No schedule available, hide the scheduleMonths
                    scheduleMonths.visibility = View.GONE
                    scheduleClockImage.visibility = View.GONE
                }
                is NoteItem.NoteAndSchedule -> {
                    val note = noteItem.noteAndSchedule.note
                    val schedule = noteItem.noteAndSchedule.schedule

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

                    // Display the schedule date if available
                    schedule?.let {
                        val calendar = it.date
                        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                        scheduleMonths.text = monthName
                        scheduleMonths.visibility = View.VISIBLE
                        scheduleClockImage.visibility = View.VISIBLE
                    } ?: run {
                        // If no schedule, hide the scheduleMonths and clock image
                        scheduleMonths.visibility = View.GONE
                        scheduleClockImage.visibility = View.GONE
                    }
                }

                else -> {}
            }
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
