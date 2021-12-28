package de.htw_berlin.barzettel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.htw_berlin.barzettel.databinding.RowCostumerOverviewBinding

class CostumerListAdapter(val onLongClick: (Int) -> Unit, val onClick: (View, Int) -> Unit) :
    ListAdapter<Costumer, CostumerListAdapter.ViewHolder>(CostumerComparator()) {


    class ViewHolder private constructor(val binding: RowCostumerOverviewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Costumer) {
            binding.kunde = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowCostumerOverviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kunde = getItem(position)
        holder.bind(kunde)

        holder.itemView.setOnClickListener {
            Log.d("List Adapter", "Item clicked " + kunde.id)
            onClick(it, kunde.id)
        }
        holder.itemView.setOnLongClickListener {
            Log.d("First Fragment", "Long click item " + position)
            onLongClick(position)
            true
        }

    }
    class CostumerComparator : DiffUtil.ItemCallback<Costumer>() {
        override fun areItemsTheSame(oldItem: Costumer, newItem: Costumer): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Costumer, newItem: Costumer): Boolean {
            return oldItem.id == newItem.id
        }
    }

}