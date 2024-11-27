/**
 * Auteur : Koestli Camille / Oliveira Vitoria
 * Description : Ce fichier contient l'implémentation de l'adaptateur pour la RecyclerView affichant les notes.
 * L'Adapter gère les données sous forme de deux types distincts (Note simple et Note avec planification),
 * met à jour la liste via DiffUtil et affiche des informations telles que le titre, le texte, et les délais relatifs.
 */

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
import java.util.concurrent.TimeUnit

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
        private val scheduleClockImage: ImageView = view.findViewById(R.id.note_state_icon)
        private val scheduleMonths: TextView = view.findViewById(R.id.note_state_text)

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

                    // Display the relative schedule date if available
                    schedule?.let {
                        val relativeTime = calculateRelativeTime(it.date)
                        scheduleMonths.text = relativeTime
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

        private fun calculateRelativeTime(scheduleDate: Calendar): String {
            val currentTime = Calendar.getInstance().timeInMillis
            val scheduleTime = scheduleDate.timeInMillis

            val diffInMillis = scheduleTime - currentTime
            val days = TimeUnit.MILLISECONDS.toDays(diffInMillis).toInt()
            val months = days / 30
            val weeks = days / 7

            return when {
                months > 0 -> "$months month${if (months > 1) "s" else ""}"
                weeks > 0 -> "$weeks week${if (weeks > 1) "s" else ""}"
                days > 0 -> "$days day${if (days > 1) "s" else ""}"
                days == 0 -> "Today"
                else -> "Late"
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
