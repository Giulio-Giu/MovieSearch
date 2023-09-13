package br.com.trabalhoomdb.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.trabalhoomdb.databinding.ItemEpisodeBinding
import br.com.trabalhoomdb.domain.models.Episode

class EpisodesAdapter(private val listEpisodes: List<Episode>) :
    RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemEpisodeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listEpisodes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = listEpisodes[position]
        holder.bindView(current)
    }

    inner class ViewHolder(private val viewBinding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindView(episode: Episode) {
            viewBinding.episodeNumber.text = episode.Episode
            viewBinding.episodeTitle.text = episode.Title
            viewBinding.episodeRating.text = episode.imdbRating
        }
    }
}