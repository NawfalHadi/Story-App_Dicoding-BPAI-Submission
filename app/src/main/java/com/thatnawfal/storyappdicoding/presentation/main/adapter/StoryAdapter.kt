package com.thatnawfal.storyappdicoding.presentation.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
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

            binding.ivItemStoryFull.load(story.photoUrl)
            binding.ivDetail.load(story.photoUrl)

            binding.tvNames.text = "${story.name}"

//            binding.cvItemStory.setOnLongClickListener {
//                binding.blackLayout.visibility = View.VISIBLE
//                binding.ivItemStoryFull.visibility = View.VISIBLE
//                true
//            }
            binding.cvItemStory.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    binding.tvNames to "TextName",
                    binding.ivDetail to "ImageDetail"
//                    binding.ivItemStoryProfile to "ImageProfile"
                )
                extras.sharedElements
                onStoryClickedCallback.storyClicked(story, extras)
            }


            binding.cvItemStory.setOnTouchListener { _, motionEvent ->
                when(motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.blackLayout.visibility = View.VISIBLE
                        binding.ivItemStoryFull.visibility = View.VISIBLE
                        true
                    }
                    else -> {
                        binding.blackLayout.visibility = View.INVISIBLE
                        binding.ivItemStoryFull.visibility = View.INVISIBLE
                        true
                    }
                }
            }

        }
    }

    interface OnStoryClickedCallback {
        fun storyClicked(story: Story, extras: FragmentNavigator.Extras)
    }

}