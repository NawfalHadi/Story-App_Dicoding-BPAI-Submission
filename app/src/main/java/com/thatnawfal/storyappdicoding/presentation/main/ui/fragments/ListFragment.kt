package com.thatnawfal.storyappdicoding.presentation.main.ui.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thatnawfal.storyappdicoding.R
import com.thatnawfal.storyappdicoding.data.remote.response.Story
import com.thatnawfal.storyappdicoding.databinding.FragmentListBinding
import com.thatnawfal.storyappdicoding.presentation.auth.bussiness.AuthenticationViewModel
import com.thatnawfal.storyappdicoding.presentation.main.adapter.StoryAdapter
import com.thatnawfal.storyappdicoding.presentation.main.bussiness.StoryViewModel
import com.thatnawfal.storyappdicoding.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding : FragmentListBinding

    private val storyViewModel: StoryViewModel by viewModels()
    private val authViewModel: AuthenticationViewModel by viewModels()

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
//        postponeEnterTransition()
        authViewModel.getSession().observe(viewLifecycleOwner){
            showStories("Bearer ${it.token}")
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_previewFragment)
        }
        binding.bgBtnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_previewFragment)
        }
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_profileFragment)
        }
    }

    private fun showStories(token: String) {
        with(binding){
            storyViewModel.getStories(token).observe(viewLifecycleOwner){
                when(it){
                    is Resource.Loading -> Log.e("Stories", "Fetching...")
                    is Resource.Success -> {
                        storyAdapter.setItem(it.payload?.listStory!!)
                        initRecyclerView()
                        fragmentlistMotionlayout.transitionToEnd()

//                        (view?.parent as? ViewGroup)?.doOnPreDraw {
//                            startPostponedEnterTransition()
//                        }
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

            // onscrollchanged to hide the btn for adding story
        }

        storyAdapter.itemListener(object : StoryAdapter.OnStoryClickedCallback {
            override fun storyClicked(story: Story, extras: FragmentNavigator.Extras) {
//                val action = ListFragmentDirections.actionListFragmentToDetailFragment(story)
                val mBundle = Bundle()
                mBundle.putParcelable(DetailFragment.STORY_KEY, story)
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, mBundle, null, extras)
            }
        })
    }
}