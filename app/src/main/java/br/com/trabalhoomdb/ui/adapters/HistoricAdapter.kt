package br.com.trabalhoomdb.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.trabalhoomdb.R

class HistoricAdapter (private val context: Context, private val list: List<String>) : RecyclerView.Adapter<HistoricAdapter.ViewHolder>() {

    lateinit var clickListener: ClickListener

    interface ClickListener {
        fun onClick(search: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_historic, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = list[position]
        holder.searchText.text = current

        holder.searchText.setOnClickListener {
            clickListener.onClick(current)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchText: TextView = itemView.findViewById(R.id.tv_itemHistoric_search)
    }
}