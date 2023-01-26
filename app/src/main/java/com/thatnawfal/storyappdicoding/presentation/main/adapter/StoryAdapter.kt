package com.thatnawfal.storyappdicoding.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thatnawfal.storyappdicoding.data.remote.response.Story
import com.thatnawfal.storyappdicoding.databinding.ItemStoryBinding

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    private lateinit var onStoryClickedCallback: OnStoryClickedCallback
    fun itemListener(onStoryClickedCallback: OnStoryClickedCallback){
        this.onStoryClickedCallback = onStoryClickedCallback
    }

    private var diffCallback = object : DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value: List<Story>) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapter.ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryAdapter.ViewHolder, position: Int) {
        holder.bindingView(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(
        private val binding : ItemStoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindingView(story: Story) {
            binding.itemListName.text = "${story.name}"
            binding.itemListName.setOnClickListener {
                onStoryClickedCallback.storyClicked(story)
            }
        }
    }

    interface OnStoryClickedCallback {
        fun storyClicked(story: Story)
    }

}