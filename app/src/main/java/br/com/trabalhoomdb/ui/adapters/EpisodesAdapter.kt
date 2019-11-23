package br.com.trabalhoomdb.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.trabalhoomdb.R
import br.com.trabalhoomdb.models.omdb.Episode

class EpisodesAdapter (private val context: Context, private val list: List<Episode>) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_episode, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = list[position]
        holder.episodeNumber.text = current.Episode
        holder.episodeTile.text = current.Title
        holder.episodeRating.text = current.imdbRating
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val episodeNumber: TextView = itemView.findViewById(R.id.episode_number)
        val episodeTile: TextView = itemView.findViewById(R.id.episode_title)
        val episodeRating: TextView = itemView.findViewById(R.id.episode_rating)
    }
}