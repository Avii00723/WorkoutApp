package aw.project.a7minuteworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aw.project.a7minuteworkout.databinding.ItemHistoryRowBinding

// Adapter class to display history items
class HistoryAdapter(private val items: List<HistoryEntity>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    // ViewHolder class to hold references to UI elements of each item
    class ViewHolder(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val llHistoryItemMain = binding.llHistoryItemMain
        val tvItem = binding.tvItem
        val tvPosition = binding.tvPosition
    }

    // Called when a new ViewHolder is created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout using ViewBinding for each row
        return ViewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // Binds data to the UI elements in the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the history entity at the current position
        val historyEntity = items[position]

        // Set position number (1-based index)
        holder.tvPosition.text = (position + 1).toString()

        // Set date of exercise from the entity
        holder.tvItem.text = historyEntity.date

        // Set background color based on even/odd position
        if (position % 2 == 0) {
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#EBEBEB"))
        } else {
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    // Returns the number of items in the list
    override fun getItemCount(): Int {
        return items.size
    }
}
