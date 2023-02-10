package com.thatnawfal.storyappdicoding.presentation.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
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
        @SuppressLint("ClickableViewAccessibility")
        fun bindingView(story: Story) {

            with(binding){
                tvNames.text = "${story.name}"

                ivItemStoryFull.load(story.photoUrl)
                ivDetail.load(story.photoUrl)

                ViewCompat.setTransitionName(cvItemStory, "idImage-${story.id}")
            }


            binding.cvItemStory.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    binding.tvNames to "TextName",
                    binding.cvItemStory to "CardImage",
                    binding.ivProfile to "ImageProfile"
                )
                extras.sharedElements
                onStoryClickedCallback.storyClicked(story, extras)
            }

            binding.btnFullscreen.setOnClickListener {
                binding.blackLayout.visibility = View.VISIBLE
                binding.ivItemStoryFull.visibility = View.VISIBLE
                binding.btnFullscreen.visibility = View.GONE
                binding.btnFullscreenExit.visibility = View.VISIBLE
            }

            binding.btnFullscreenExit.setOnClickListener {
                binding.blackLayout.visibility = View.INVISIBLE
                binding.ivItemStoryFull.visibility = View.INVISIBLE
                binding.btnFullscreen.visibility = View.VISIBLE
                binding.btnFullscreenExit.visibility = View.GONE

            }
        }
    }

    interface OnStoryClickedCallback {
        fun storyClicked(story: Story, extras: FragmentNavigator.Extras)
    }

}