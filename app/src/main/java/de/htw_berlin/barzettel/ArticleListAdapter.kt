package de.htw_berlin.barzettel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import de.htw_berlin.barzettel.databinding.RowCostumerDetailBinding

class ArticleListAdapter(val onMinus: (Int) -> Unit, val onPlus: (Int) -> Unit, val customer: LiveData<Customer>):
    ListAdapter<Article, ArticleListAdapter.ViewHolder>(ArticleComparator()){

    class ViewHolder private constructor(val binding: RowCostumerDetailBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Article, customer: LiveData<Customer>) {
            binding.article = item
            binding.costumer = customer
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowCostumerDetailBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListAdapter.ViewHolder {
        return ArticleListAdapter.ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticleListAdapter.ViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article, customer)
        holder.binding.plus.setOnClickListener {
            onPlus(article.id)
            holder.binding.invalidateAll()
        }
        holder.binding.minus.setOnClickListener {
            onMinus(article.id)
            holder.binding.invalidateAll()
        }
    }

    class ArticleComparator : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }
    }
}