package br.com.trabalhoomdb.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.trabalhoomdb.databinding.ItemHistoricBinding
import br.com.trabalhoomdb.presentation.eventclick.EventClickItemHistoric

class HistoricAdapter(private val listSearch: List<String>) :
    RecyclerView.Adapter<HistoricAdapter.ViewHolder>() {

    //    lateinit var clickListener: ClickListener
//
//    interface ClickListener {
//        fun onClick(search: String)
//    }
    var eventClickItemHistoric: EventClickItemHistoric? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val view = LayoutInflater.from(context).inflate(R.layout.item_historic, parent, false)
        val viewBinding = ItemHistoricBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listSearch.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = listSearch[position]
        holder.bindView(current)
       // holder.searchText.text = current

        //holder.searchText.setOnClickListener {
//            clickListener.onClick(current)
        //}
    }

    inner class ViewHolder(private val viewBinding: ItemHistoricBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindView (item: String) {
            viewBinding.tvItemHistoricSearch.text = item
            viewBinding.tvItemHistoricSearch.setOnClickListener {
                eventClickItemHistoric?.onClickItemHistoric(item)
            }
        }
    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val searchText: TextView = itemView.findViewById(R.id.tv_itemHistoric_search)
//    }
}