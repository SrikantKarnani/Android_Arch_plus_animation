package com.example.anew.features.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.anew.R
import com.example.anew.databinding.FragmentTrendingBinding
import com.example.anew.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class TrendingRepoFragment : Fragment(R.layout.fragment_trending) {
    private val viewModel by viewModels<TrendingViewModel>()
    private lateinit var binding: FragmentTrendingBinding
    private val adapter = TrendingAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrendingBinding.bind(view)
        binding.repoRecycler.adapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.root.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                if (startId == R.id.searchConstraintSet && endId == R.id.end) {
                    binding.tiel.text = null
                    hideKeyboard(binding.root)
                }
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })
        if (viewModel.fetchedTrendingRepos.value == null)
            GlobalScope.launch(Dispatchers.IO) {
                delay(1000)
                withContext(Dispatchers.Main) {
                    viewModel.fetchTrendingRepos(1)
                }
            }
        viewModel.fetchedTrendingRepos.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.repoRecycler.visibility = View.VISIBLE
                binding.languageSpinner.visibility = View.VISIBLE
                adapter.submitList(it.items)
            } ?: run {
                binding.repoRecycler.visibility = View.GONE
                adapter.submitList(emptyList())
            }
        })
        binding.btnRetry.setOnClickListener {
            viewModel.fetchTrendingRepos(
                1,
                "language:" + binding.languageSpinner.selectedItem as String
            )
        }
        binding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.fetchTrendingRepos(
                        1,
                        "language:" + binding.languageSpinner.selectedItem as String
                    )
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        binding.tiel.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    adapter.submitList(viewModel.filterList(p0?.toString()).apply {
                        if (this.isEmpty()) {
                            binding.ivNoResult.visibility = View.VISIBLE
                        } else {
                            binding.ivNoResult.visibility = View.GONE
                        }
                    })
                    binding.repoRecycler.scrollToPosition(0)
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
    }
}