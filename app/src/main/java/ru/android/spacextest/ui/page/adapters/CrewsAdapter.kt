package ru.android.spacextest.ui.page.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ru.android.spacextest.R
import ru.android.spacextest.utils.RecyclerViewClickListener
import ru.android.spacextest.databinding.CrewItemBinding
import ru.android.spacextest.models.CrewsModel
import ru.android.spacextest.models.LaunchesModel

class CrewsAdapter (): RecyclerView.Adapter<CrewsAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<CrewsModel>() {
        override fun areItemsTheSame(oldItem: CrewsModel, newItem: CrewsModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CrewsModel, newItem: CrewsModel): Boolean =
            oldItem == newItem
    }

    val itemViewModels = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewsAdapter.ViewHolder {
        val binding = CrewItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CrewsAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = itemViewModels.currentList.size

    inner class ViewHolder(val binding: CrewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: Int) {
            val model = itemViewModels.currentList[pos]

            if(model.linkImage.isNullOrEmpty())
                binding.ivCrewAvatarItem.load(R.drawable.placeholder)
            else
                binding.ivCrewAvatarItem.load(model.linkImage) {
                    crossfade(true)
                    placeholder(R.color.grey_light_2)
                    error(R.drawable.placeholder)
                    transformations(CircleCropTransformation())
                }
            binding.tvNameCrewItem.text = model.name
            binding.tvAgencyItem.text = model.agency
            binding.tvStatusCrewItem.text = model.status
        }

    }
}