package de.htw_berlin.barzettel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.htw_berlin.barzettel.databinding.RowCostumerOverviewBinding
import de.htw_berlin.barzettel.databinding.RowSalesOfDayBinding

class SalesMonthListAdapter : ListAdapter<SalesOfDay, SalesMonthListAdapter.ViewHolder>(SalesOfDayComparator()) {

    class ViewHolder private constructor(val binding: RowSalesOfDayBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SalesOfDay) {
            binding.sales = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowSalesOfDayBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesMonthListAdapter.ViewHolder {
        return SalesMonthListAdapter.ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SalesMonthListAdapter.ViewHolder, position: Int) {
        val sale = getItem(position)
        holder.bind(sale)
    }

    class SalesOfDayComparator : DiffUtil.ItemCallback<SalesOfDay>() {
        override fun areItemsTheSame(oldItem: SalesOfDay, newItem: SalesOfDay): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SalesOfDay, newItem: SalesOfDay): Boolean {
            return oldItem.date == newItem.date
        }
    }

}