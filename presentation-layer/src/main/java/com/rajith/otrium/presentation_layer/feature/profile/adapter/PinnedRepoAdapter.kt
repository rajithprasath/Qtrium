package com.rajith.otrium.presentation_layer.feature.profile.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rajith.otrium.domain_layer.domain.Edge
import com.rajith.otrium.presentation_layer.R
import com.rajith.otrium.presentation_layer.databinding.PinnedRepositoryItemBinding

/**
 * The Class will bind the all pinned repositories which fetched from the api and show it as a list
 */

class PinnedRepoAdapter(private val context: Context) :
    RecyclerView.Adapter<PinnedRepoAdapter.PinnedRepoViewHolder>() {
    /**
     * The list of repositories of the adapter
     */
    private var repos: List<Edge> = listOf()

    override fun getItemCount(): Int {
        return repos.size
    }

    /**
     * Updates the list of repositories of the adapter
     * @param repos the new list of repositories of the adapter
     */
    fun updateRepositories(repos: List<Edge>) {
        this.repos = repos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinnedRepoViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: PinnedRepositoryItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.pinned_repository_item, parent, false)
        return PinnedRepoViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: PinnedRepoViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    /**
     * The ViewHolder of the adapter
     * @property binding the DataBinging object for Repository item
     */
    class PinnedRepoViewHolder(
        private val binding: PinnedRepositoryItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Binds a Repository into the view
         */
        fun bind(node: Edge) {
            binding.edge = node
            binding.ivOval.setColorFilter(Color.parseColor(node.node.primaryLanguage.color))
            Glide.with(context)
                .load(node.node.owner.avatarUrl)
                .into(binding.ivAvatar)
            binding.executePendingBindings()
        }
    }

}