package aw.project.a7minuteworkout

import  android.graphics.Color
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import aw.project.a7minuteworkout.databinding.ActivityHistoryBinding
import aw.project.a7minuteworkout.databinding.ItemHistoryRowBinding
import android.view.ViewGroup
import androidx.core.content.ContextCompat


class HistoryAdapter(private val items:ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    class ViewHolder(binding: ItemHistoryRowBinding):RecyclerView.ViewHolder(binding.root){
        val llHistoryItemMain=binding.llHistoryItemMain
        val tvItem=binding.tvItem
        val tvPosition=binding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(
            LayoutInflater.from(parent.context),parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date:String=items.get(position)
        holder.tvPosition.text=(position +1).toString()
        holder.tvItem.text=date

        if (position%2==0){
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#EBEBEB")
            )
        }
        else{
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}