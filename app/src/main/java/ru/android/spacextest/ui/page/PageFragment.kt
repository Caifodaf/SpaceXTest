package ru.android.spacextest.ui.page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import ru.android.spacextest.R
import ru.android.spacextest.databinding.PageFragmentBinding
import ru.android.spacextest.models.LaunchesModel
import ru.android.spacextest.ui.BaseFragment
import ru.android.spacextest.ui.page.adapters.CrewsAdapter
import ru.android.spacextest.utils.SpaceItemDecoration
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat

@AndroidEntryPoint
class PageFragment : BaseFragment() {

    companion object{
        internal const val ERROR_VISIBILITY_SHOW_CREWLIST = 0
        internal const val LOADER_VISIBILITY_SHOW_CREWLIST = 1
        internal const val CREWLIST_VISIBILITY = 2
    }

    private var _binding: PageFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PageViewModel>()

    private var crewsAdapter: CrewsAdapter? = null
    private lateinit var list: LaunchesModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutBase(binding.flLoaderBlock,
            binding.flErrorBlock,
            binding.nsvMaimBlockPage)

        loadBundleParameters()
    }

    private fun loadBundleParameters() {
        val str = requireArguments().getString("list")
        if (!str.isNullOrEmpty()) {
            val collectionType: Type = object : TypeToken<LaunchesModel>() {}.type
            list = Gson().fromJson(str, collectionType)

            if (!list.crews.isNullOrEmpty()) {
                viewModel.idsCrews = list.crews

                observeError()
                observeListCrews()
            } else {
                binding.tvCrewsTitlePage.visibility = View.GONE
                binding.rvCrewsList.visibility = View.GONE
                binding.progressbarLoadCrewList.visibility = View.GONE
            }

            initUI()

        }else{
            errorViewVisibility(ERROR_VISIBILITY_SHOW)
        }
    }

    private fun observeListCrews(){
        viewModel.listCrews.observe(viewLifecycleOwner, Observer {
            initRecyclerView()

            crewsListVisibility(CREWLIST_VISIBILITY)
        })
    }

    private fun observeError(){
        viewModel.errorUpdate.observe(viewLifecycleOwner, Observer {
            crewsListVisibility(ERROR_VISIBILITY_SHOW_CREWLIST)
        })
    }

    private fun initRecyclerView(){
        crewsAdapter = CrewsAdapter()

        binding.rvCrewsList.addItemDecoration(SpaceItemDecoration())
        binding.rvCrewsList.adapter = crewsAdapter

        crewsAdapter!!.itemViewModels.submitList(viewModel.listCrews.value!!)
    }

    @SuppressLint("SimpleDateFormat")
    private fun initUI() {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatOut: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = dateFormat.parse(list.date)

        if (list.links.patch.largeLinkImage.isNullOrEmpty())
            binding.ivLargeLogoMission.load(R.drawable.placeholder)
        else
            binding.ivLargeLogoMission.load(list.links.patch.largeLinkImage) {
                crossfade(true)
                placeholder(R.color.grey_light_2)
                error(R.drawable.placeholder)
            }

        binding.tvNameMission.text = list.name
        binding.tvCoresFlightNum.text = list.core[0].flight
        binding.tvStatusMission.text = when (list.successStatus) {
            true -> requireContext().getString(R.string.status_success_launchers_page)
            false -> requireContext().getString(R.string.status_failure_launchers_page)
            else -> requireContext().getString(R.string.status_is_unknown_launchers_page)
        }
        binding.tvDateMission.text = dateFormatOut.format(date!!)

        if (!list.details.isNullOrEmpty())
            binding.tvDetailsDescription.text = list.details
        else
            binding.llBlockDetails.visibility = View.GONE

        errorViewVisibility(MINE_VISIBILITY)
    }

    private fun crewsListVisibility(type:Int){
        when(type){
            ERROR_VISIBILITY_SHOW_CREWLIST->{
                binding.llErrorCrewsList.visibility = View.VISIBLE
                binding.progressbarLoadCrewList.visibility = View.GONE
                binding.rvCrewsList.visibility = View.GONE
            }
            LOADER_VISIBILITY_SHOW_CREWLIST->{
                binding.llErrorCrewsList.visibility = View.GONE
                binding.progressbarLoadCrewList.visibility = View.VISIBLE
                binding.rvCrewsList.visibility = View.GONE
            }
            CREWLIST_VISIBILITY->{
                binding.llErrorCrewsList.visibility = View.GONE
                binding.progressbarLoadCrewList.visibility = View.GONE
                binding.rvCrewsList.visibility = View.VISIBLE
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        crewsAdapter = null
        _binding = null
    }
}