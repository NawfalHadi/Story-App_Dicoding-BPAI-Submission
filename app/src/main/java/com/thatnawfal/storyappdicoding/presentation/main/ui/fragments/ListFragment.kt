package com.thatnawfal.storyappdicoding.presentation.main.ui.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thatnawfal.storyappdicoding.R
import com.thatnawfal.storyappdicoding.data.remote.response.Story
import com.thatnawfal.storyappdicoding.databinding.FragmentListBinding
import com.thatnawfal.storyappdicoding.presentation.main.adapter.StoryAdapter
import com.thatnawfal.storyappdicoding.presentation.main.bussiness.StoryViewModel
import com.thatnawfal.storyappdicoding.presentation.main.ui.HomeActivity
import com.thatnawfal.storyappdicoding.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding : FragmentListBinding

    private val storyViewModel: StoryViewModel by viewModels()
    private val storyAdapter: StoryAdapter by lazy { StoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)
        showStories()
    }

    private fun showStories() {
        with(binding){
            storyViewModel.getStories(HomeActivity.TEMP_TOKEN).observe(viewLifecycleOwner){
                when(it){
                    is Resource.Loading -> Log.e("Stories", "Fetching...")
                    is Resource.Success -> {
                        storyAdapter.setItem(it.payload?.listStory!!)
                        initRecyclerView()
                        fragmentlistMotionlayout.transitionToEnd()
                    }
                    is Resource.Empty -> Log.e("Stories", "Empty")
                    is Resource.Error -> Log.e("Stories", it.message.toString())
                }
            }
        }
    }

    private fun initRecyclerView() {
        with(binding){
            rvStories.setHasFixedSize(true)
            rvStories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvStories.adapter = storyAdapter
        }

        storyAdapter.itemListener(object : StoryAdapter.OnStoryClickedCallback {
            override fun storyClicked(story: Story, extras: FragmentNavigator.Extras) {
                val mBundle = Bundle()
                mBundle.putParcelable(DetailFragment.STORY_KEY, story)
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, mBundle, null, extras)
            }
        })
    }
}