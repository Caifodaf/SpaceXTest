package ru.android.spacextest.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import ru.android.spacextest.R
import ru.android.spacextest.utils.RecyclerViewClickListener
import ru.android.spacextest.databinding.MainFragmentBinding
import ru.android.spacextest.ui.BaseFragment
import ru.android.spacextest.ui.main.MainViewModel.Companion.ERROR_LOAD
import ru.android.spacextest.ui.main.MainViewModel.Companion.ERROR_MORE_LOAD
import ru.android.spacextest.ui.main.adapters.LaunchersAdapter

@AndroidEntryPoint
class MainFragment : BaseFragment(), RecyclerViewClickListener {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutBase(binding.flLoaderBlock,
            binding.flErrorBlockMain,
            binding.nsvMaimBlockPage)

        initButtons()

        viewModel.initRecyclerView(binding.rvList,this)

        endlessLoadRecyclerView()
        observeLaunchersList()
        observeError()
    }

    private fun endlessLoadRecyclerView() {
        binding.nsvMaimBlockPage.setOnScrollChangeListener { v: NestedScrollView, _: Int, y: Int, _: Int, _: Int ->
            if (y == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (viewModel.page != viewModel.totalPage)
                    viewModel.loadMoreData()
                else {
                    binding.progressbarRvList.visibility = View.GONE
                    binding.tvAllRvList.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeLaunchersList(){
        viewModel.listLaunches.observe(viewLifecycleOwner, Observer {

            viewModel.launchersAdapter.itemViewModels.submitList(viewModel.allMutableListLaunchers)
            //(binding.rvList.adapter as LaunchersAdapter).itemViewModels.submitList(viewModel.allMutableListLaunchers)
            viewModel.launchersAdapter.notifyDataSetChanged()
            errorViewVisibility(MINE_VISIBILITY)
        })
    }

    private fun observeError(){
        viewModel.errorUpdate.observe(viewLifecycleOwner, Observer {
            when(viewModel.errorUpdate.value!![0].type){
                ERROR_LOAD -> errorViewVisibility(ERROR_VISIBILITY_SHOW)
                ERROR_MORE_LOAD ->{
                    binding.btnReloadMoreMain.visibility = View.VISIBLE
                    binding.progressbarRvList.visibility = View.GONE
                }
            }
        })
    }

    private fun initButtons(){
        binding.btnReloadMain.setOnClickListener{
            errorViewVisibility(LOADER_VISIBILITY_SHOW)
            viewModel.getLaunchesList()
        }
        binding.btnReloadMoreMain.setOnClickListener{
            binding.btnReloadMoreMain.visibility = View.GONE
            binding.progressbarRvList.visibility = View.VISIBLE
            viewModel.loadMoreData()
        }
    }

    override fun onRecyclerViewItemClick(view: View, list: Any?) {
        val bundle = Bundle()
        val jsonList = Gson().toJson(list)
        bundle.putString("list", jsonList)

        when (view.id) {
            R.id.cl_main_block_launcher_item ->{
                findNavController().navigate(R.id.pageFragment,bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}