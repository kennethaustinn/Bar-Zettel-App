package de.htw_berlin.barzettel

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.dalmagrov.barzettel.Costumer

class CostumerListAdapter(val onLongClick: (Int) -> Unit, val onClick: (View, Int) -> Unit) : ListAdapter<Costumer, CostumerListAdapter.MyViewHolder>(
    CostumerComparator()
) {


    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val textName : TextView
        val textPrice : TextView
        val context : Context

        init {
            textName = view.findViewById(R.id.listName)
            textPrice = view.findViewById(R.id.listPrice)
            context = view.context
        }

        companion object {
            fun create(parent: ViewGroup): MyViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_costumer_overview, parent, false)
                return MyViewHolder(view)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val kunde = getItem(position)
        holder.textName.text = kunde.name
        val dprice = kunde.price/100.0
        val df = java.text.DecimalFormat("#0.00")
        holder.textPrice.text = df.format(dprice) + " €"
        holder.textPrice.setTextColor(Color.RED)
        holder.textName.setTextColor(Color.RED)

        holder.itemView.setOnClickListener {
            Log.d("List Adapter", "Item clicked " + position)
            onClick(it, position)
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