package com.thatnawfal.storyappdicoding.presentation.main.ui.fragments

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import coil.load
import com.thatnawfal.storyappdicoding.R
import com.thatnawfal.storyappdicoding.data.remote.response.Story
import com.thatnawfal.storyappdicoding.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private lateinit var storyData : Story
    private val navArg : DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

//        storyData = navArg.story
        storyData = arguments?.getParcelable(STORY_KEY)!!
        bindingView(storyData)

    }

    private fun bindingView(story: Story) {
        with(binding){
            ivDetail.load(story.photoUrl).apply {
                ViewCompat.setTransitionName(cardviewDetail, "CardImage")
            }
            ivOriginal.load(story.photoUrl)


            tvNames.text = story.name
            tvDesc.text = story.description

            btnFullscreen.setOnClickListener {
                blackLayout.visibility = View.VISIBLE
                ivOriginal.visibility = View.VISIBLE
                btnFullscreen.visibility = View.GONE
                btnFullscreenExit.visibility = View.VISIBLE
            }

            btnFullscreenExit.setOnClickListener {
                blackLayout.visibility = View.INVISIBLE
                ivOriginal.visibility = View.INVISIBLE
                btnFullscreen.visibility = View.VISIBLE
                btnFullscreenExit.visibility = View.GONE
            }

        }
    }

    companion object {
        const val STORY_KEY = "story_key"
    }
}